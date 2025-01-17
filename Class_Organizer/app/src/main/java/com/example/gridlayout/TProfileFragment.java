package com.example.gridlayout;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */


public class TProfileFragment extends Fragment {


    private TextView Tname,Temail,Tcontact;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    private String userId,dept,courses;


    public TProfileFragment()
    {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tprofile, container, false);

        mAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();


        Tname=view.findViewById(R.id.TnameID);
        Tcontact=view.findViewById(R.id.TcontactID);
        Temail=view.findViewById(R.id.TemailID);




        userId=mAuth.getCurrentUser().getUid();

        Bundle teacher= getArguments();
        if(teacher!=null){

            dept=teacher.getString("DEPT");

        }


        //Toast.makeText(getContext(), "TP- "+dept, Toast.LENGTH_SHORT).show();

        mFirestore.collection(dept).document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


                String s;

                String name=documentSnapshot.getString("NAME");
                String course=documentSnapshot.getString("COURSES");
                String Email=documentSnapshot.getString("EMAIL");
                String Contact=documentSnapshot.getString("CONTACT");





                Tname.setText(name);
                Tcontact.setText(Contact);
                Temail.setText(Email);



            }
        });







        return view;
    }


}

