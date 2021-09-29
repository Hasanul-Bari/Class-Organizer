package com.example.gridlayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class dept_cse extends AppCompatActivity implements View.OnClickListener {


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private TextView tnav_headertvName,tnav_headertvEmail;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private String userId;
    private AlertDialog.Builder alertdialogbuilder;

    //schedule button
    private Button _cse32_scheduleButton;
    //reschedule button
    private Button _cse32_rescheduleButton;

    //session text view

    private TextView _cse32Session,_cse32Level,_AdminPanel;
    String dept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_cse);

        _cse32_scheduleButton=findViewById(R.id.cse32_scheduleButton);
        /*        _cse32_rescheduleButton=findViewById(R.id.cse32_rescheduleButton);*/
        _cse32Level=findViewById(R.id.cse32Level);
        _cse32Session=findViewById(R.id.cse32Session);
        _AdminPanel=findViewById(R.id.AdminPanel);

        _cse32_scheduleButton.setOnClickListener(this);
//        _cse32_rescheduleButton.setOnClickListener(this);

        Intent intent=getIntent();
        dept=intent.getStringExtra("Dept");
        _AdminPanel.setText("\nAdminPanel-"+dept+"\n");


        Toast.makeText(getApplicationContext(),"AP-"+dept,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.cse32_scheduleButton){
            String SESSION=_cse32Session.getText().toString();
            //Toast.makeText(getApplicationContext(),"ScheduleButton 2",Toast.LENGTH_LONG).show();

         Intent intent=new Intent(dept_cse.this,cse_32.class);
            intent.putExtra("LevelSemester","\nLevel: 3 Semester: II\n");
            intent.putExtra("Session",SESSION);
            intent.putExtra("Dept",dept);
            intent.putExtra("Track","32");
            startActivity(intent);
            Toast.makeText(getApplicationContext(),"ScheduleButton 2",Toast.LENGTH_LONG).show();
        }
//        else if(v.getId()==R.id.cse32_rescheduleButton){
//            Toast.makeText(getApplicationContext(),"RE ScheduleButton",Toast.LENGTH_LONG).show();
//
//        }



    }
}
