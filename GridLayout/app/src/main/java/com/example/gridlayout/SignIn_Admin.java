package com.example.gridlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignIn_Admin extends AppCompatActivity implements View.OnClickListener{

    private EditText adminName,adminPW;
    private Button signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_admin);

        adminName=findViewById(R.id.adminNameId);
        adminPW=findViewById(R.id.adminPwId);
        signInButton=findViewById(R.id.AdminSignInButton);

        signInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.AdminSignInButton){

            String username= adminName.getText().toString().trim();
            String password=adminPW.getText().toString().trim();

            if(username.isEmpty()) {
                adminName.setError("Enter an email address");
                adminName.requestFocus();
                return;
            }
            if(password.isEmpty()) {
                adminPW.setError("Enter a password");
                adminPW.requestFocus();
                return;
            }

            if(username.equals("admin")==false) {
                adminName.setError("Invalid username");
                adminName.requestFocus();
                return;
            }

            if(password.equals("admin")==false) {
                adminPW.setError("Invalid password");
                adminPW.requestFocus();
                return;
            }

            Intent intent=new Intent(SignIn_Admin.this,Admin_Menu.class);
            startActivity(intent);
        }

    }
}