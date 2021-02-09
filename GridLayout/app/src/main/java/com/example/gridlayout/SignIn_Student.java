package com.example.gridlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn_Student extends AppCompatActivity implements View.OnClickListener {

    private EditText username1,password1;
    private TextView SgotoSignup;
    private Button SsignInButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in__student);


        mAuth = FirebaseAuth.getInstance();

        this.setTitle("Sign In");

        SgotoSignup=findViewById(R.id.SGotosignUP);
        username1=findViewById(R.id.signInemail);
        password1=findViewById(R.id.signInpassword);
        SsignInButton=findViewById(R.id.SsignInButton);


        SsignInButton.setOnClickListener(this);
        SgotoSignup.setOnClickListener(this);

    }

    /*protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            String usrId=mAuth.getCurrentUser().getUid();

            Intent intent =new Intent(SignIn_Student.this,Student.class);
            intent.putExtra("user",usrId);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }

    }*/


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.SGotosignUP:
                finish();
                Intent intent=new Intent(getApplicationContext(),SignUP_Student.class);
                startActivity(intent);
                break;

            case R.id.SsignInButton:
                userSignin();
                break;



        }

    }

    private void userSignin() {



        String username= "student."+username1.getText().toString().trim();
        String password=password1.getText().toString().trim();



        //checking the validity of the email
        if(username.isEmpty())
        {
            username1.setError("Enter an email address");
            username1.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches())
        {
            username1.setError("Enter a valid email address");
            username1.requestFocus();
            return;
        }

        //checking the validity of the password
        if(password.isEmpty())
        {
            password1.setError("Enter a password");
            password1.requestFocus();
            return;
        }






        //  Toast.makeText(getApplicationContext(),"Hello !!! ",Toast.LENGTH_SHORT).show();



        mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    //  Toast.makeText(getApplicationContext(),"------Hello !!! ",Toast.LENGTH_SHORT).show();

                    String usrId=mAuth.getCurrentUser().getUid();
                    finish();
                    Intent intent =new Intent(SignIn_Student.this,Student.class);
                    intent.putExtra("user",usrId);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);

                }
                else{
                    Toast.makeText(getApplicationContext(),"Login Unsuccessfull ",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}

