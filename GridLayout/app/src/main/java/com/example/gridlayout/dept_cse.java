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
    private Button _cse32_scheduleButton,_cse31_scheduleButton,_cse21_scheduleButton,_cse22_scheduleButton,_cse11_scheduleButton,_cse12_scheduleButton,_cse41_scheduleButton,_cse42_scheduleButton;
    //reschedule button


    //session text view

    private TextView _cse32Session,_cse32Level,_AdminPanel,_cse31Level,_cse22Level,_cse21Level,_cse12Level,_cse11Level,_cse42Level,_cse41Level;
    String dept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_cse);

        _cse11_scheduleButton=findViewById(R.id.cse11_scheduleButton);
        _cse11Level=findViewById(R.id.cse11Level);



        _cse12_scheduleButton=findViewById(R.id.cse12_scheduleButton);
        _cse12Level=findViewById(R.id.cse12Level);


        _cse21_scheduleButton=findViewById(R.id.cse21_scheduleButton);
        _cse21Level=findViewById(R.id.cse21Level);

        _cse22_scheduleButton=findViewById(R.id.cse22_scheduleButton);
        _cse22Level=findViewById(R.id.cse22Level);



        _cse31_scheduleButton=findViewById(R.id.cse31_scheduleButton);
        _cse31Level=findViewById(R.id.cse31Level);

        _cse32_scheduleButton=findViewById(R.id.cse32_scheduleButton);
        _cse32Level=findViewById(R.id.cse32Level);


        _cse41_scheduleButton=findViewById(R.id.cse41_scheduleButton);
        _cse41Level=findViewById(R.id.cse41Level);

        _cse42_scheduleButton=findViewById(R.id.cse42_scheduleButton);
        _cse42Level=findViewById(R.id.cse42Level);




       // _cse32Session=findViewById(R.id.cse32Session);
        _AdminPanel=findViewById(R.id.AdminPanel);

        _cse11_scheduleButton.setOnClickListener(this);
        _cse12_scheduleButton.setOnClickListener(this);

        _cse21_scheduleButton.setOnClickListener(this);
        _cse22_scheduleButton.setOnClickListener(this);

        _cse31_scheduleButton.setOnClickListener(this);
        _cse32_scheduleButton.setOnClickListener(this);

        _cse41_scheduleButton.setOnClickListener(this);
        _cse42_scheduleButton.setOnClickListener(this);


        Intent intent=getIntent();
        dept=intent.getStringExtra("Dept");
        _AdminPanel.setText("\nAdminPanel-"+dept+"\n");


        //Toast.makeText(getApplicationContext(),"AP-"+dept,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {

        //all levels added by tanverlikhon 18 11 21

        if(v.getId()==R.id.cse11_scheduleButton){
            // String SESSION=_cse31Session.getText().toString();
            //Toast.makeText(getApplicationContext(),"ScheduleButton 2",Toast.LENGTH_LONG).show();

            Intent intent=new Intent(dept_cse.this,cse_32.class);
            intent.putExtra("LevelSemester","\nLevel: 1 Semester: I\n");
            //     intent.putExtra("Session",SESSION);
            intent.putExtra("Dept",dept);
            intent.putExtra("Track","11");
            startActivity(intent);
            // Toast.makeText(getApplicationContext(),"ScheduleButton 2",Toast.LENGTH_LONG).show();
        }
        else if(v.getId()==R.id.cse12_scheduleButton){
            // String SESSION=_cse32Session.getText().toString();
            //Toast.makeText(getApplicationContext(),"ScheduleButton 2",Toast.LENGTH_LONG).show();

            Intent intent=new Intent(dept_cse.this,cse_32.class);
            intent.putExtra("LevelSemester","\nLevel: 1 Semester: II\n");
            //     intent.putExtra("Session",SESSION);
            intent.putExtra("Dept",dept);
            intent.putExtra("Track","12");
            startActivity(intent);
            // Toast.makeText(getApplicationContext(),"ScheduleButton 2",Toast.LENGTH_LONG).show();
        }


        if(v.getId()==R.id.cse21_scheduleButton){
            // String SESSION=_cse31Session.getText().toString();
            //Toast.makeText(getApplicationContext(),"ScheduleButton 2",Toast.LENGTH_LONG).show();

            Intent intent=new Intent(dept_cse.this,cse_32.class);
            intent.putExtra("LevelSemester","\nLevel: 2 Semester: I\n");
            //     intent.putExtra("Session",SESSION);
            intent.putExtra("Dept",dept);
            intent.putExtra("Track","21");
            startActivity(intent);
            // Toast.makeText(getApplicationContext(),"ScheduleButton 2",Toast.LENGTH_LONG).show();
        }
        else if(v.getId()==R.id.cse22_scheduleButton){
            // String SESSION=_cse32Session.getText().toString();
            //Toast.makeText(getApplicationContext(),"ScheduleButton 2",Toast.LENGTH_LONG).show();

            Intent intent=new Intent(dept_cse.this,cse_32.class);
            intent.putExtra("LevelSemester","\nLevel: 2 Semester: II\n");
            //     intent.putExtra("Session",SESSION);
            intent.putExtra("Dept",dept);
            intent.putExtra("Track","22");
            startActivity(intent);
            // Toast.makeText(getApplicationContext(),"ScheduleButton 2",Toast.LENGTH_LONG).show();
        }


        if(v.getId()==R.id.cse31_scheduleButton){
           // String SESSION=_cse31Session.getText().toString();
            //Toast.makeText(getApplicationContext(),"ScheduleButton 2",Toast.LENGTH_LONG).show();

            Intent intent=new Intent(dept_cse.this,cse_32.class);
            intent.putExtra("LevelSemester","\nLevel: 3 Semester: I\n");
       //     intent.putExtra("Session",SESSION);
            intent.putExtra("Dept",dept);
            intent.putExtra("Track","31");
            startActivity(intent);
           // Toast.makeText(getApplicationContext(),"ScheduleButton 2",Toast.LENGTH_LONG).show();
        }
        else if(v.getId()==R.id.cse32_scheduleButton){
           // String SESSION=_cse32Session.getText().toString();
            //Toast.makeText(getApplicationContext(),"ScheduleButton 2",Toast.LENGTH_LONG).show();

            Intent intent=new Intent(dept_cse.this,cse_32.class);
            intent.putExtra("LevelSemester","\nLevel: 3 Semester: II\n");
            //     intent.putExtra("Session",SESSION);
            intent.putExtra("Dept",dept);
            intent.putExtra("Track","32");
            startActivity(intent);
            // Toast.makeText(getApplicationContext(),"ScheduleButton 2",Toast.LENGTH_LONG).show();
        }


        if(v.getId()==R.id.cse41_scheduleButton){
            // String SESSION=_cse31Session.getText().toString();
            //Toast.makeText(getApplicationContext(),"ScheduleButton 2",Toast.LENGTH_LONG).show();

            Intent intent=new Intent(dept_cse.this,cse_32.class);
            intent.putExtra("LevelSemester","\nLevel: 4 Semester: I\n");
            //     intent.putExtra("Session",SESSION);
            intent.putExtra("Dept",dept);
            intent.putExtra("Track","41");
            startActivity(intent);
            // Toast.makeText(getApplicationContext(),"ScheduleButton 2",Toast.LENGTH_LONG).show();
        }
        else if(v.getId()==R.id.cse42_scheduleButton){
            // String SESSION=_cse32Session.getText().toString();
            //Toast.makeText(getApplicationContext(),"ScheduleButton 2",Toast.LENGTH_LONG).show();

            Intent intent=new Intent(dept_cse.this,cse_32.class);
            intent.putExtra("LevelSemester","\nLevel: 4 Semester: II\n");
            //     intent.putExtra("Session",SESSION);
            intent.putExtra("Dept",dept);
            intent.putExtra("Track","42");
            startActivity(intent);
            // Toast.makeText(getApplicationContext(),"ScheduleButton 2",Toast.LENGTH_LONG).show();
        }


    }
}
