package com.example.gridlayout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SGroupFragment extends Fragment {

    String dept,level,semester,name;
    private TextView intro;

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

        intro=view.findViewById(R.id.dept_lev_sem);
        intro.setText(dept+" : "+level+"_"+semester);




        return view;
    }
}