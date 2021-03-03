package com.example.gridlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button stu,teacher;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private String usrId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();



        stu=findViewById(R.id.button2);
        teacher=findViewById(R.id.button);

        stu.setOnClickListener(this);
        teacher.setOnClickListener(this);

    }

    //Hasan : Auto login part

    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()!=null){
            usrId=mAuth.getCurrentUser().getUid();


            mFirestore.collection("UserType").document(usrId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    String type=documentSnapshot.getString("Type");

                    if(type.equals("Teacher")){

                        finish();
                        Intent intent =new Intent(MainActivity.this,Teacher.class);
                        intent.putExtra("user",usrId);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(intent);

                    }
                    else if(type.equals("Student")){

                        finish();
                        Intent intent =new Intent(MainActivity.this,Student.class);
                        intent.putExtra("user",usrId);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(intent);


                    }
                }
            });



        }

    }



    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.button){

            //Toast.makeText(getApplicationContext(),"entered33",Toast.LENGTH_SHORT).show();

            Intent intent=new Intent(MainActivity.this,SignIn.class);
            startActivity(intent);

           // Toast.makeText(getApplicationContext(),"entered156",Toast.LENGTH_SHORT).show();


        }
        else if(v.getId()==R.id.button2){
//
            Intent intent=new Intent(MainActivity.this,SignIn_Student.class);
           startActivity(intent);

        }

    }


}
