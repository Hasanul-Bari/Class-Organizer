package com.example.gridlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class CR_SignUP extends AppCompatActivity implements View.OnClickListener {

    private EditText email, password1, name, contact, ID;
    private Button signupCR;
    private TextView tv;
    private FirebaseAuth mAuth;
    private ProgressBar pb;
    private Spinner spinner1, spinner2, spinner;
    private String dept = "SELECT_DEPARTMENT", level = "SELECT_LEVEL", sem = "SELECT_SEMESTER";
    private DatabaseReference databaseReference;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cr_signup);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        this.setTitle("CR Sign UP");


        email = findViewById(R.id.CREID);
        password1 = findViewById(R.id.CRP);
        signupCR = findViewById(R.id.CRSU);
        //tv=findViewById(R.id.SgotosignIn);

        pb = findViewById(R.id.CRPB);
        ID = findViewById(R.id.CRID);

        name = findViewById(R.id.CRFN);
        contact = findViewById(R.id.CRC);


        signupCR.setOnClickListener(this);
        //  tv.setOnClickListener(this);


        //spinner for department

        spinner = (Spinner) findViewById(R.id.CRSPD);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Department, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        //spinner for level
        spinner1 = (Spinner) findViewById(R.id.CRSPL);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.LEVEL, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);


        //spiner for semester
        spinner2 = (Spinner) findViewById(R.id.CRSPS);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.SEMESTER, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dept = spinner.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), dept, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                level = spinner1.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), level, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sem = spinner2.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), sem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {


            case R.id.CRSU:
                userRegister();
                break;


        }

    }

    private void userRegister() {

        final String SID = ID.getText().toString().trim();
        final String username = email.getText().toString().trim();


        String password = password1.getText().toString().trim();
        final String name1 = name.getText().toString().trim();
        final String contact1 = contact.getText().toString().trim();


        //checking the validity of the email
        if (username.isEmpty()) {
            email.setError("Enter an email address");
            email.requestFocus();
            return;
        }

        if (SID.isEmpty() || SID.length() != 7) {
            ID.setError("Enter a valid student ID");
            ID.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            email.setError("Enter a valid email address");
            email.requestFocus();
            return;
        }

        //checking the validity of the password
        if (password.isEmpty()) {
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
        if (dept.equals("SELECT_DEPARTMENT")) {
            Toast.makeText(getApplicationContext(), "Select a Department", Toast.LENGTH_SHORT).show();
            spinner.requestFocus();
            return;


        }
        if (level.equals("SELECT_LEVEL")) {
            Toast.makeText(getApplicationContext(), "Select level", Toast.LENGTH_SHORT).show();
            spinner1.requestFocus();
            return;


        }
        if (sem.equals("SELECT_SEMESTER")) {
            Toast.makeText(getApplicationContext(), "Select semester", Toast.LENGTH_SHORT).show();
            spinner2.requestFocus();
            return;


        }


        pb.setVisibility(View.VISIBLE);
        //username is actually the email id here
        mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pb.setVisibility(View.GONE);
                if (task.isSuccessful()) {


                    /////cloud firestore part


                    //Toast.makeText(getApplicationContext(),usrId,Toast.LENGTH_SHORT).show();
                    String usrId = mAuth.getCurrentUser().getUid();
                    String CR_NAME = name1;
                    String course_ = dept;


                    Map<String, Object> profile = new HashMap<>();
                    profile.put("NAME", name1);
                    profile.put("DEPT", dept);
                    profile.put("CONTACT", contact1);
                    profile.put("EMAIL", username);
                    profile.put("ID", SID);
                    profile.put("CR", "YES");
                    profile.put("LEVEL", level);
                    profile.put("SEM", sem);

                    db.collection("STUDENT").document(usrId).set(profile)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Data Stored Successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(getApplicationContext(), "Failed to Store data", Toast.LENGTH_SHORT).show();


                                }
                            });


                    //updated by Hasan (add user type);

                    Map<String, Object> type = new HashMap<>();
                    type.put("Type", "Student");

                    db.collection("UserType").document(usrId).set(type)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Data Stored Successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(getApplicationContext(), "Failed to Store data", Toast.LENGTH_SHORT).show();


                                }
                            });


                    /////cloud firestore part end


                    Toast.makeText(getApplicationContext(), "Register is Successful", Toast.LENGTH_SHORT).show();
                    finish();

                    Intent intent = new Intent(CR_SignUP.this, Admin_Menu.class);

                    // intent.putExtra("course_code",usrId);
                    intent.putExtra("user", usrId);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);

                } else {

                    //Toast.makeText(getApplicationContext(),"Register is Unsuccessful",Toast.LENGTH_SHORT).show();

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "Email is already registered", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(getApplicationContext(), "Error : " + task.getException(), Toast.LENGTH_SHORT).show();


                }

            }
        });

    }
}

