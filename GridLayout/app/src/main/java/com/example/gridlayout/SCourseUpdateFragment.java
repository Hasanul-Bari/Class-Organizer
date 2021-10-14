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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SCourseUpdateFragment extends Fragment {

    String dept,level,semester,name;

    private TextView intro;

    private RecyclerView recyclerView;

    private List<PostItem> postList;
    private PostAdapter postAdapter;

    DatabaseReference databaseReference;



    public SCourseUpdateFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_s_course_update, container, false);

        //Getting data send by intend
        Bundle bd = getArguments();
        if (bd != null) {

            dept = bd.getString("DEPT");
            level = bd.getString("LEVEL");
            semester = bd.getString("SEM");
            name=bd.getString("NAME");
        }

        intro=view.findViewById(R.id.dept_lev_sem);
        intro.setText(dept+" : "+level+"_"+semester+"\nCourse Updates");

        recyclerView=view.findViewById(R.id.recylerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(semester.equals("I")){
            semester="1";
        }
        else if(semester.equals("II")){
            semester="2";
        }


        databaseReference= FirebaseDatabase.getInstance().getReference("Posts/"+dept+level+semester);

        postList= new ArrayList<>();

        // reading posts from firebase

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                postList.clear();

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    PostItem postItem=dataSnapshot.getValue(PostItem.class);


                    postList.add(postItem);



                }

                postAdapter=new PostAdapter(getActivity(),postList,null);
                recyclerView.setAdapter(postAdapter);

                recyclerView.scrollToPosition(postList.size()-1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView.scrollToPosition(postList.size()-1);



        return view;
    }
}