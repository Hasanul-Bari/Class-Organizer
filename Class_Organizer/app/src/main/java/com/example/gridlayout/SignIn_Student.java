package com.example.gridlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class SignIn_Student extends AppCompatActivity implements View.OnClickListener {

    private EditText username1,password1;
    private TextView SgotoSignup;
    private Button SsignInButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseFirestore mFirestore;

    private String usrId;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in__student);


        mAuth = FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();


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



        String username= username1.getText().toString().trim();
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

                    //Hasan : check if user is student?

                    usrId=mAuth.getCurrentUser().getUid();


                    mFirestore.collection("UserType").document(usrId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            String type=documentSnapshot.getString("Type");

                            if(type.equals("Student")){

                                subscribeToStudent();

                                finish();
                                Intent intent =new Intent(SignIn_Student.this,Student.class);
                                intent.putExtra("user",usrId);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                startActivity(intent);
                            }
                            else{
                                FirebaseAuth.getInstance().signOut();
                                Toast.makeText(getApplicationContext(),"This email is not registered with students ",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });




                }
                else{
                    Toast.makeText(getApplicationContext(),"Login Unsuccessfull ",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void subscribeToStudent(){

        FirebaseMessaging.getInstance().subscribeToTopic("student")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            //msg = getString(R.string.msg_subscribe_failed);
                        }
                        //Log.d(TAG, msg);
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


       


    }

    /*private void unsubscribeFromStudent(){

        FirebaseMessaging.getInstance().unsubscribeFromTopic("student").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }*/

}

