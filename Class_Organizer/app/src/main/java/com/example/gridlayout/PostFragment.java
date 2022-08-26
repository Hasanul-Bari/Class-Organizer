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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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


public class PostFragment extends Fragment implements View.OnClickListener {

    private String dept,level,semester,class_,code,title,tid,tname;

    private TextView introView;
    private EditText postWrite;
    private Button postButton;
    private RecyclerView recyclerView;

    private List<PostItem> postLists;
    private PostAdapter postAdapter;

    DatabaseReference databaseReference1,databaseReference2;
    FirebaseAuth mAuth;

    public PostFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_post, container, false);

        //Getting data send by intend
        Bundle bd = getArguments();
        if (bd != null) {

            dept = bd.getString("dept");
            level = bd.getString("lev");
            semester = bd.getString("sem");

            code=bd.getString("Code");
            title=bd.getString("Title");
            class_=bd.getString("Class_");

            tname=bd.getString("tname");

        }

        if(semester.equals("1")){
            semester="I";
        }
        else if(semester.equals("2")){
            semester="II";
        }



        String intro=code+"\n"+title+"\n"+"Dept: "+dept+"   Level: "+level+"   Semester: "+semester;

        introView=view.findViewById(R.id.introId);

        introView.setText(intro);

        postWrite=view.findViewById(R.id.editPostId);
        postButton=view.findViewById(R.id.postButtonId);

        recyclerView=view.findViewById(R.id.recylerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAuth=FirebaseAuth.getInstance();
        tid=mAuth.getCurrentUser().getUid();

        databaseReference1= FirebaseDatabase.getInstance().getReference("Posts/"+class_);
        databaseReference2= FirebaseDatabase.getInstance().getReference("Posts/"+class_);

        postLists= new ArrayList<>();

        // reading posts from firebase

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                postLists.clear();

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    PostItem postItem=dataSnapshot.getValue(PostItem.class);

                    if(postItem.getCourseCode().equals(code)){
                        Log.d(TAG, "onDataChange: "+postItem.getCourseCode());
                        postLists.add(postItem);
                    }


                }

                postAdapter=new PostAdapter(getActivity(),postLists,tid);
                recyclerView.setAdapter(postAdapter);

                recyclerView.scrollToPosition(postLists.size()-1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView.scrollToPosition(postLists.size()-1);
        postButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.postButtonId){
            post();
            recyclerView.scrollToPosition(postLists.size()-1);
        }
    }



    private void post() {

        String content=postWrite.getText().toString().trim();

        //get sending date
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("MMM dd, yyyy, hh:mm aaa");
        String time = sdf.format(new Date());

        Log.d(TAG, "sendMessage: "+time);

        PostItem postItem=new PostItem(tid,tname, content,time,code,title);

        String fileId= databaseReference1.push().getKey();

        databaseReference1.child(fileId).setValue(postItem).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //Toast.makeText(getActivity(),"Message sent successfully",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(),"Post failed",Toast.LENGTH_SHORT).show();
                }
            }
        });


        postWrite.setText("");


    }




}