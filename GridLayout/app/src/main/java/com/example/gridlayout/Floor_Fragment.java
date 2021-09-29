package com.example.gridlayout;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class Floor_Fragment extends Fragment implements View.OnClickListener {

    Button f0, f1, f2, f3, f4, f_reset;
    private FirebaseFirestore mFirestore, mFirestore1;
    String CR,dept,level,semester;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public Floor_Fragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_floor_, container, false);

        //floor buttons
        f0 = view.findViewById(R.id.F0);
        f1 = view.findViewById(R.id.F1);
        f2 = view.findViewById(R.id.F2);
        f3 = view.findViewById(R.id.F3);
        f4 = view.findViewById(R.id.F4);
        //f_reset = view.findViewById(R.id.Reset_F);


        //action listeners
        f0.setOnClickListener(this);
        f1.setOnClickListener(this);
        f2.setOnClickListener(this);
        f3.setOnClickListener(this);
        f4.setOnClickListener(this);
        //f_reset.setOnClickListener(this);


        Bundle FLOOR= getArguments();
        if(FLOOR!=null){
            CR=FLOOR.getString("CR");
            dept=FLOOR.getString("DEPT");
            level=FLOOR.getString("LEVEL");
            semester=FLOOR.getString("SEM");
        }


        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.F0:
                Toast.makeText(getActivity(),"Ground Floor ! "+CR,Toast.LENGTH_SHORT).show();
                sendingData("Ground Floor",CR);


                break;
            case R.id.F1:
                sendingData("1st Floor",CR);

                break;
            case R.id.F2:
                sendingData("2nd Floor",CR);

                break;
            case R.id.F3:
                sendingData("3rd Floor",CR);


                break;
            case R.id.F4:
                sendingData("4th Floor",CR);
                break;


            case R.id.Reset_F:




                break;

            default:
                break;
        }
    }
    void sendingData(String F,String C){

        FragmentTransaction fragmentTransaction= getFragmentManager().beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("Floor", F);
        bundle.putString("CR",C);
        bundle.putString("DEPT",dept);
        bundle.putString("LEVEL",level);
        bundle.putString("SEM",semester);

// Set Fragmentclass Arguments
        Fragment ROOM = new RoomFragment();
        ROOM.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment_container,ROOM).commit();

    }





}