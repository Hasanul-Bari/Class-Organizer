package com.example.gridlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Admin_Menu extends AppCompatActivity implements View.OnClickListener {


    Button faculties,classrooms,add_cr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);


        //buttons
        faculties= findViewById(R.id.FacultyList);
        classrooms= findViewById(R.id.ClassRooms);


        add_cr= findViewById(R.id.Add_CR);



        ///listeners

        faculties.setOnClickListener(this);
        classrooms.setOnClickListener(this);
        add_cr.setOnClickListener(this);

    }







    @Override
    public void onClick(View view) {

        Intent intent;
        switch (view.getId()){
            case R.id.FacultyList:
                intent=new Intent(Admin_Menu.this,Faculty.class);
                //Toast.makeText(getApplicationContext(), "Faculty clicked", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case R.id.ClassRooms:
                intent=new Intent(Admin_Menu.this,ClassRoom_F.class);
                //Toast.makeText(getApplicationContext(), "Faculty clicked", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;

            case R.id.Add_CR:
                intent=new Intent(Admin_Menu.this,CR_SignUP.class);
                startActivity(intent);

                //Toast.makeText(getApplicationContext(), "Faculty clicked", Toast.LENGTH_SHORT).show();
                break;


            default:
                break;


        }


    }
}