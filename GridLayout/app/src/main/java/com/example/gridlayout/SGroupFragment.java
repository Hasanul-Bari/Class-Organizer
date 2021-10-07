package com.example.gridlayout;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SGroupFragment extends Fragment implements View.OnClickListener {

    String dept,level,semester,name,uid;
    private TextView intro;
    private EditText messageWrite;
    private ImageButton sendButton;
    private RecyclerView recyclerView;
    private ScrollView mScrollView;

    private List<MessageItem> messageList;
    private MessageAdapter messageAdapter;

    DatabaseReference databaseReference1,databaseReference2;
    FirebaseAuth mAuth;

    public SGroupFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_sgroup, container, false);


        //Getting data send by intend
        Bundle bd = getArguments();
        if (bd != null) {

            dept = bd.getString("DEPT");
            level = bd.getString("LEVEL");
            semester = bd.getString("SEM");
            name=bd.getString("NAME");
        }

        mAuth=FirebaseAuth.getInstance();
        uid=mAuth.getCurrentUser().getUid();

        intro=view.findViewById(R.id.dept_lev_sem);
        intro.setText(dept+" : "+level+"_"+semester);

        messageWrite=view.findViewById(R.id.messageId);
        sendButton=view.findViewById(R.id.sendButtonId);


        recyclerView=view.findViewById(R.id.recylerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        databaseReference1= FirebaseDatabase.getInstance().getReference("Groups/"+dept+"_"+level+"_"+semester);
        databaseReference2= FirebaseDatabase.getInstance().getReference("Groups/"+dept+"_"+level+"_"+semester);

        messageList=new ArrayList<>();

        //read messages from firebase and attach to adapter

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messageList.clear();

                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){

                    MessageItem messageItem=dataSnapshot1.getValue(MessageItem.class);
                    messageList.add(messageItem);
                }

                //Toast.makeText(getActivity(),"Messages load successfully" ,Toast.LENGTH_SHORT).show();

                messageAdapter=new MessageAdapter(getActivity(),messageList,uid);
                recyclerView.setAdapter(messageAdapter);

                recyclerView.scrollToPosition(messageList.size()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        recyclerView.scrollToPosition(messageList.size()-1);




        sendButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.sendButtonId){
            sendMessage();
            recyclerView.scrollToPosition(messageList.size()-1);
        }

    }

    private void sendMessage() {

        String msg=messageWrite.getText().toString().trim();

        //get sending date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());




        MessageItem messageItem=new MessageItem(msg,name, date,uid);

        String fileId= databaseReference1.push().getKey();

        databaseReference1.child(fileId).setValue(messageItem).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //Toast.makeText(getActivity(),"Message sent successfully",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(),"Message sent failed",Toast.LENGTH_SHORT).show();
                }
            }
        });


        messageWrite.setText("");


    }
}