package com.example.gridlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Faculty extends AppCompatActivity implements View.OnClickListener {
    Button _CSE,_EEE,_ECE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);

        _CSE=findViewById(R.id.CSE);
        _EEE=findViewById(R.id.EEE);
        _ECE=findViewById(R.id.ECE);

        _CSE.setOnClickListener(this);
        _EEE.setOnClickListener(this);
        _ECE.setOnClickListener(this);




    }


  @Override
   public void onClick(View v) {
      if(v.getId()==R.id.CSE){
            Intent intent=new Intent(Faculty.this,dept_cse.class);
            intent.putExtra("Dept","CSE");
            startActivity(intent);
        }
       else if(v.getId()==R.id.EEE){
          Intent intent=new Intent(Faculty.this,dept_cse.class);
            intent.putExtra("Dept","EEE");
            startActivity(intent);
        }
        else if(v.getId()==R.id.ECE){
          Intent intent=new Intent(Faculty.this,dept_cse.class);
            intent.putExtra("Dept","ECE");
            startActivity(intent);
        }
    }
}
