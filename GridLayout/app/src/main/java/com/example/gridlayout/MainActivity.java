package com.example.gridlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button stu,teacher;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //   mAuth = FirebaseAuth.getInstance();
        stu=findViewById(R.id.button2);
        teacher=findViewById(R.id.button);

        stu.setOnClickListener(this);
        teacher.setOnClickListener(this);

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
