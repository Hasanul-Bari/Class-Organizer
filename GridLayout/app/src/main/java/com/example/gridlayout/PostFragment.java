package com.example.gridlayout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PostFragment extends Fragment {

    private String dept,level,semester,class_,code,title;

    private TextView introView;

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



        return view;
    }
}