
package com.example.gridlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Floor0 extends AppCompatActivity implements View.OnClickListener {

   private TextView _R1,_R2,_R3,_R4,_R5,_R6,_R7,_R8,_R9,_R10,_R;  //Room no buttons
   /* Button _R1V,_R2V,_R3V,_R4V,_R5V,_R6V,_R7V,_R8V,_R9V,_R10V;  //Room Vacant buttons
    Button _R1C,_R2C,_R3C,_R4C,_R5C,_R6C,_R7C,_R8C,_R9C,_R10C;  //Room book buttons*/
    TextView _R1S,_R2S,_R3S,_R4S,_R5S,_R6S,_R7S,_R8S,_R9S,_R10S;  //Room status
    String Room,FL;
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore=FirebaseFirestore.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor0);



        ////Room no text views

        _R1=findViewById(R.id.R1);
        _R2=findViewById(R.id.R2);
        _R3=findViewById(R.id.R3);
        _R4=findViewById(R.id.R4);
        _R5=findViewById(R.id.R5);
        _R6=findViewById(R.id.R6);
        _R7=findViewById(R.id.R7);
        _R8=findViewById(R.id.R8);
        _R9=findViewById(R.id.R9);
        _R10=findViewById(R.id.R10);
        _R=findViewById(R.id.FloorNo);


        ////Room status text views

        _R1S=findViewById(R.id.R1S);
        _R2S=findViewById(R.id.R2S);
        _R3S=findViewById(R.id.R3S);
        _R4S=findViewById(R.id.R4S);
        _R5S=findViewById(R.id.R5S);
        _R6S=findViewById(R.id.R6S);
        _R7S=findViewById(R.id.R7S);
        _R8S=findViewById(R.id.R8S);
        _R9S=findViewById(R.id.R9S);
        _R10S=findViewById(R.id.R10S);



        //////room vacant buttons

/*        _R1V=findViewById(R.id.R1V);
        _R2V=findViewById(R.id.R2V);
        _R3V=findViewById(R.id.R3V);
        _R4V=findViewById(R.id.R4V);
        _R5V=findViewById(R.id.R5V);
        _R6V=findViewById(R.id.R6V);
        _R7V=findViewById(R.id.R7V);
        _R8V=findViewById(R.id.R8V);
        _R9V=findViewById(R.id.R9V);
        _R10V=findViewById(R.id.R10V);

        _R1V.setOnClickListener(this);
        _R2V.setOnClickListener(this);
        _R3V.setOnClickListener(this);
        _R4V.setOnClickListener(this);
        _R5V.setOnClickListener(this);
        _R6V.setOnClickListener(this);
        _R7V.setOnClickListener(this);
        _R8V.setOnClickListener(this);
        _R9V.setOnClickListener(this);
        _R10V.setOnClickListener(this);

        /////room book buttons
        _R1C=findViewById(R.id.R1C);
        _R2C=findViewById(R.id.R2C);
        _R3C=findViewById(R.id.R3C);
        _R4C=findViewById(R.id.R4C);
        _R5C=findViewById(R.id.R5C);
        _R6C=findViewById(R.id.R6C);
        _R7C=findViewById(R.id.R7C);
        _R8C=findViewById(R.id.R8C);
        _R9C=findViewById(R.id.R9C);
        _R10C=findViewById(R.id.R10C);

        _R1C.setOnClickListener(this);
        _R2C.setOnClickListener(this);
        _R3C.setOnClickListener(this);
        _R4C.setOnClickListener(this);
        _R5C.setOnClickListener(this);
        _R6C.setOnClickListener(this);
        _R7C.setOnClickListener(this);
        _R8C.setOnClickListener(this);
        _R9C.setOnClickListener(this);
        _R10C.setOnClickListener(this);*/

        //getting intent from classroom_F

      Intent intent=getIntent();
      Room= intent.getStringExtra("Room");
      FL= intent.getStringExtra("Floor");
      _R.setText(intent.getStringExtra("Heading")+"Floor");


       String str1[]={"01","02","03","04","05","06","07","08","09","10"};
int i=0;
        _R1.setText(Room + str1[i++]);
        _R2.setText(Room + str1[i++]);
        _R3.setText(Room + str1[i++]);
        _R4.setText(Room + str1[i++]);
        _R5.setText(Room + str1[i++]);
        _R6.setText(Room + str1[i++]);
        _R7.setText(Room + str1[i++]);
        _R8.setText(Room + str1[i++]);
        _R9.setText(Room + str1[i++]);
        _R10.setText(Room + str1[i++]);



        mFirestore.collection(FL).document("R1").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("status");
                _R1S.setText(status);

            }

        });

        mFirestore.collection(FL).document("R2").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("status");
                _R2S.setText(status);

            }

        });

        mFirestore.collection(FL).document("R3").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("status");
                _R3S.setText(status);
                // Toast.makeText(getActivity(), "entered + "+status+FL, Toast.LENGTH_SHORT).show();

            }

        });

        mFirestore.collection(FL).document("R4").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("status");
                _R4S.setText(status);

            }

        });

        mFirestore.collection(FL).document("R5").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("status");
                _R5S.setText(status);

            }

        });

        mFirestore.collection(FL).document("R6").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("status");
                _R6S.setText(status);

            }

        });


        mFirestore.collection(FL).document("R7").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("status");
                _R7S.setText(status);
            }

        });


        mFirestore.collection(FL).document("R8").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("status");
                _R8S.setText(status);

            }

        });


        mFirestore.collection(FL).document("R9").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("status");
                _R9S.setText(status);

            }

        });


        mFirestore.collection(FL).document("R10").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("status");
                _R10S.setText(status);

            }

        });




    }


    @Override
    public void onClick(View view) {

    }
    public void setRoomNo(TextView tv,String ss){
        tv.setText(ss);

    }
}