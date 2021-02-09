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
public class SProfileFragment extends Fragment {


    private TextView  Sname,Sid,Semail,Scontact,Sdept;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;


    private String userId;


    public SProfileFragment()
    {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sprofile, container, false);

        mAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();



        Sname=view.findViewById(R.id.SnameID);
        Sid=view.findViewById(R.id.SID_id);
        Sdept=view.findViewById(R.id.SdeptID);
        Scontact=view.findViewById(R.id.SContactId);
        Semail=view.findViewById(R.id.SEmailid);






        userId=mAuth.getCurrentUser().getUid();



        mFirestore.collection("STUDENT").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String s;

                String name=documentSnapshot.getString("NAME");
                String stuId=documentSnapshot.getString("ID");
                String ContactNo=documentSnapshot.getString("CONTACT");
                String dept=documentSnapshot.getString("DEPT");
                String Email=documentSnapshot.getString("EMAIL");

                Email=Email.replaceFirst("student.","");






                Sname.setText(name);
                Sid.setText(stuId);
                Sdept.setText(dept);
                Semail.setText(Email);
                Scontact.setText(ContactNo);

            }
        });



        return view;
    }


}

