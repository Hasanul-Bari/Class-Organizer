package com.example.gridlayout;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */


public class TProfileFragment extends Fragment {


    private TextView Tname,Tcourse,Temail,Tcontact;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    private String userId;


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
        Tcourse=view.findViewById(R.id.TcourseId);
        Temail=view.findViewById(R.id.TemailID);




        userId=mAuth.getCurrentUser().getUid();



        mFirestore.collection("TEACHER").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


                String s;

                String name=documentSnapshot.getString("NAME");
                String course=documentSnapshot.getString("COURSE");
                String Email=documentSnapshot.getString("EMAIL");
                String Contact=documentSnapshot.getString("CONTACT");





                Tname.setText(name);
                Tcourse.setText(course);
                Tcontact.setText(Contact);
                Temail.setText(Email);



            }
        });







        return view;
    }


}
