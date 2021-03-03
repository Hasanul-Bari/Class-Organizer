package com.example.gridlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText email,password1,name,contact;
    private TextView tv1;
    private Button signIn1;

    private FirebaseAuth mAuth;
    private ProgressBar pb;
    private Spinner spinner;
    private String course="SELECT_COURSE";
    private DatabaseReference databaseReference;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference("Teachers");

        this.setTitle("Sign Up");

        tv1=findViewById(R.id.signUptv);
        email =findViewById(R.id.signUpemail);
        password1=findViewById(R.id.signUpassword);
        signIn1=findViewById(R.id.signUp);

        pb=findViewById(R.id.progressBar);

        name=findViewById(R.id.FullName);
        contact=findViewById(R.id.contact);


        signIn1.setOnClickListener(this);
        tv1.setOnClickListener(this);


          spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Courses, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course=spinner.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(),course,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {



            }
        });


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.signUptv:
                finish();
                Intent intent=new Intent(getApplicationContext(),SignIn.class);
                startActivity(intent);
                break;

            case R.id.signUp:

                userRegister();

                break;



        }

    }

    private void userRegister() {

        final String username= email.getText().toString().trim();
        String password=password1.getText().toString().trim();
        final String name1=name.getText().toString().trim();
        final String contact1=contact.getText().toString().trim();




        //checking the validity of the email
        if(username.isEmpty())
        {
            email.setError("Enter an email address");
            email.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches())
        {
            email.setError("Enter a valid email address");
            email.requestFocus();
            return;
        }

        //checking the validity of the password
        if(password.isEmpty())
        {
            password1.setError("Enter a password");
            password1.requestFocus();
            return;
        }



        if (name1.isEmpty()) {
            name.setError("Name field is Empty :(");
            name.requestFocus();
            return;
        }

        if (password.length() < 6) {
            password1.setError("Password length is less than 6");
            password1.requestFocus();
            return;
        }

        if (contact1.isEmpty()) {
            contact.setError("Contact field is Empty :(");
            contact.requestFocus();
            return;
        }

        if (contact1.length() != 11) {
            contact.setError("Enter 11 digit phone number");
            contact.requestFocus();
            return;
        }
        if(course.equals("SELECT_COURSE")){
            Toast.makeText(getApplicationContext(),"Select a Course",Toast.LENGTH_SHORT).show();
            spinner.requestFocus();
            return;


        }




        pb.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pb.setVisibility(View.GONE);
                if (task.isSuccessful()) {

               User_SIR user_sir= new User_SIR(name1,username,course,contact1);


               /////cloud firestore part


                    //Toast.makeText(getApplicationContext(),usrId,Toast.LENGTH_SHORT).show();
                    String usrId=mAuth.getCurrentUser().getUid();
                    String sir_name=name1;
                    String course_=course;


                    Map<String, Object> profile = new HashMap<>();
                    profile.put("NAME",sir_name);
                    profile.put("COURSE",course_);
                    profile.put("CONTACT",contact1);
                    profile.put("EMAIL",username);

                    db.collection("TEACHER").document(usrId).set(profile)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(),"Data Stored Successfully",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(getApplicationContext(),"Failed to Store data",Toast.LENGTH_SHORT).show();


                                }
                            });

                    //updated by Hasan (add user type);

                    Map<String, Object> type = new HashMap<>();
                    type.put("Type","Teacher");

                    db.collection("UserType").document(usrId).set(type)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(),"Data Stored Successfully",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(getApplicationContext(),"Failed to Store data",Toast.LENGTH_SHORT).show();


                                }
                            });










                    /////cloud firestore part end



///realtime db part starts
                   /* String str=databaseReference.push().getKey();
                  databaseReference
                            .child(str)
                            .setValue(user_sir).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Successfully stored data", Toast.LENGTH_LONG).show();
                            } else {

                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();

                                //display a failure message
                            }
                        }
                    });*/
//realtime db part end


                    Toast.makeText(getApplicationContext(),"Register is Successfull",Toast.LENGTH_SHORT).show();
                    finish();

                    Intent intent =new Intent(SignUp.this,Teacher.class);

                   // intent.putExtra("course_code",usrId);
                    intent.putExtra("user",usrId);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);

                } else {

                    //Toast.makeText(getApplicationContext(),"Register is Unsuccessfull",Toast.LENGTH_SHORT).show();

                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"Email is already registered",Toast.LENGTH_SHORT).show();

                    }
                    else
                        Toast.makeText(getApplicationContext(),"Error : "+task.getException(),Toast.LENGTH_SHORT).show();


                }

            }
        });

    }
}
