package com.example.gridlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Admin_Schedule extends AppCompatActivity implements View.OnClickListener {
    String dept, level, session, track, DAY, day;

    TextView _A10t, _A11t, _A12t, _A14t, _A15t, _A16t, _Class_Schedule;

    Button _OK10, _OK11, _OK12, _OK14, _OK15, _OK16;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore, mFirestore1;
    private Spinner spinner1, spinner2, spinner3, spinner4, spinner5, spinner6, temp_spinner;
    private String DeptInfo, CourseCode1, CourseCode2, CourseCode3, CourseCode4, CourseCode5, CourseCode6,temp_title;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_schedule);

        //Course title Text view

        _A10t = findViewById(R.id._Admin10t);
        _A11t = findViewById(R.id._Admin11t);
        _A12t = findViewById(R.id._Admin12t);
        _A14t = findViewById(R.id._Admin14t);
        _A15t = findViewById(R.id._Admin15t);
        _A16t = findViewById(R.id._Admin16t);




        //ok buttons
        _OK10 = findViewById(R.id.OK10);
        _OK11 = findViewById(R.id.OK11);
        _OK12 = findViewById(R.id.OK12);
        _OK14 = findViewById(R.id.OK14);
        _OK15 = findViewById(R.id.OK15);
        _OK16 = findViewById(R.id.OK16);




        _Class_Schedule = findViewById(R.id.Class_Schedule);


        Intent intent = getIntent();
        dept = intent.getStringExtra("Dept");
        track = intent.getStringExtra("Track");
        day = intent.getStringExtra("Day");

        String heading = make_heading(dept, track, day);

        _Class_Schedule.setText(heading);

        DAY = dept + track + "_" + day;

        DeptInfo = dept + track;


        //Toast.makeText(getApplicationContext(), "BEFORE Firestore " + DAY, Toast.LENGTH_LONG).show();

       mAuth = FirebaseAuth.getInstance();
       mFirestore = FirebaseFirestore.getInstance();

        ///13092021 start


/*        mFirestore.collection("Sunday").document("10-11").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String CurrentCourse = documentSnapshot.getString("CURRENT");
                Toast.makeText(getApplicationContext(), "Success  " + CurrentCourse, Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed " + e, Toast.LENGTH_LONG).show();
            }
        });*/


        /////13092021 end
       mFirestore.collection("Sunday").document("10-11").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String CurrentCourse = documentSnapshot.getString("CURRENT");
                String MainCourse = documentSnapshot.getString("CODE");
                String Status = documentSnapshot.getString("STATUS");
                String Req = documentSnapshot.getString("REQ");
                String Title=documentSnapshot.getString("Title");
                ViewCourse(CurrentCourse,Title, _A10t);

              //  Toast.makeText(getApplicationContext(), CurrentCourse+"001 "+MainCourse, Toast.LENGTH_LONG).show();

            }
        });
        mFirestore.collection(DAY).document("11-12").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String CurrentCourse = documentSnapshot.getString("CURRENT");
                String MainCourse = documentSnapshot.getString("CODE");
                String Status = documentSnapshot.getString("STATUS");
                String Req = documentSnapshot.getString("REQ");
                String Title=documentSnapshot.getString("Title");
                ViewCourse(CurrentCourse,Title, _A11t);
             //   Toast.makeText(getApplicationContext(), CurrentCourse+"002 "+MainCourse, Toast.LENGTH_LONG).show();

            }
        });
        mFirestore.collection(DAY).document("12-13").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String CurrentCourse = documentSnapshot.getString("CURRENT");
                String MainCourse = documentSnapshot.getString("CODE");
                String Status = documentSnapshot.getString("STATUS");
                String Req = documentSnapshot.getString("REQ");
                String Title=documentSnapshot.getString("Title");
                ViewCourse(CurrentCourse,Title, _A12t);
              //  Toast.makeText(getApplicationContext(), CurrentCourse+"003 "+MainCourse, Toast.LENGTH_LONG).show();
            }
        });
        mFirestore.collection(DAY).document("14-15").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String CurrentCourse = documentSnapshot.getString("CURRENT");
                String MainCourse = documentSnapshot.getString("CODE");
                String Status = documentSnapshot.getString("STATUS");
                String Req = documentSnapshot.getString("REQ");
                String Title=documentSnapshot.getString("Title");
                ViewCourse(CurrentCourse,Title, _A14t);
                //Toast.makeText(getApplicationContext(), CurrentCourse+"004 "+MainCourse, Toast.LENGTH_LONG).show();
            }
        });
        mFirestore.collection(DAY).document("15-16").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String CurrentCourse = documentSnapshot.getString("CURRENT");
                String MainCourse = documentSnapshot.getString("CODE");
                String Status = documentSnapshot.getString("STATUS");
                String Req = documentSnapshot.getString("REQ");
                String Title=documentSnapshot.getString("Title");
                ViewCourse(CurrentCourse,Title, _A15t);

            }
        });
        mFirestore.collection(DAY).document("16-17").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String CurrentCourse = documentSnapshot.getString("CURRENT");
                String MainCourse = documentSnapshot.getString("CODE");
                String Status = documentSnapshot.getString("STATUS");
                String Req = documentSnapshot.getString("REQ");
                String Title=documentSnapshot.getString("Title");
                ViewCourse(CurrentCourse,Title, _A16t);

            }
        });
     //  Toast.makeText(getApplicationContext(),"AFTER Firestore",Toast.LENGTH_LONG).show();


        //  Editing schedule
        spinner1 = findViewById(R.id.Course_Drop1);
        spinner2 = findViewById(R.id.Course_Drop2);
        spinner3 = findViewById(R.id.Course_Drop3);
        spinner4 = findViewById(R.id.Course_Drop4);
        spinner5 = findViewById(R.id.Course_Drop5);
        spinner6 = findViewById(R.id.Course_Drop6);

        _OK10.setOnClickListener(this);
        _OK11.setOnClickListener(this);
        _OK12.setOnClickListener(this);
        _OK14.setOnClickListener(this);
        _OK15.setOnClickListener(this);
        _OK16.setOnClickListener(this);



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array._CSE32Courses, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CourseCode1 = spinner1.getSelectedItem().toString();

//                Toast.makeText(getApplicationContext(),"SETspinner after "+" cc "+CourseCode,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array._CSE32Courses, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CourseCode2 = spinner2.getSelectedItem().toString();

//                Toast.makeText(getApplicationContext(),"SETspinner after "+" cc "+CourseCode,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array._CSE32Courses, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CourseCode3 = spinner3.getSelectedItem().toString();

//                Toast.makeText(getApplicationContext(),"SETspinner after "+" cc "+CourseCode,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array._CSE32Courses, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CourseCode4 = spinner4.getSelectedItem().toString();

//                Toast.makeText(getApplicationContext(),"SETspinner after "+" cc "+CourseCode,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this, R.array._CSE32Courses, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(adapter5);
        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CourseCode5 = spinner5.getSelectedItem().toString();

//                Toast.makeText(getApplicationContext(),"SETspinner after "+" cc "+CourseCode,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(this, R.array._CSE32Courses, android.R.layout.simple_spinner_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner6.setAdapter(adapter6);
        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CourseCode6 = spinner6.getSelectedItem().toString();

//                Toast.makeText(getApplicationContext(),"SETspinner after "+" cc "+CourseCode,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void SET_COURSE(String dd, String tt, final String COURSE, TextView tv) {

      //  Toast.makeText(getApplicationContext(), "SETCOURSE " + dd + tt + " cc " + COURSE, Toast.LENGTH_SHORT).show();


        //fetching course title
        mFirestore.collection(dept+track+"_COURSES").document(COURSE).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                temp_title =documentSnapshot.getString("Title");
                Toast.makeText(getApplicationContext(),dept+track+"_COURSES->"+temp_title,Toast.LENGTH_LONG).show();

            }
        });




        Map<String, Object> profile = new HashMap<>();
        profile.put("CODE", COURSE);
        profile.put("CURRENT", COURSE);
        profile.put("REQ", "");
        profile.put("STATUS", "-1");
        profile.put("Title",temp_title);

        tv.setText(COURSE+"\n"+temp_title);
        db.collection(dd).document(tt).set(profile)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Data Stored Successfully", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getApplicationContext(), "Failed to Store data Try again!", Toast.LENGTH_SHORT).show();

                    }
                });

    }

    public void ViewCourse(String MainCourse,String Title, TextView tv) {

        Toast.makeText(getApplicationContext(), " View course enter Main Course " + MainCourse, Toast.LENGTH_SHORT).show();
        if (MainCourse == null) tv.setText("NO CLASS");
        else if (MainCourse.length() == 0 | MainCourse == "SELECT_COURSE") tv.setText("NO CLASS");
        else {
            tv.setText(MainCourse+"\n"+Title);
        }


    }

/*    public  ArrayAdapter<CharSequence> CourseList(String deptInfo){
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this,
                R.array._CSE32Courses, android.R.layout.simple_spinner_item);;

        switch (deptInfo){
            case "CSE32":
                adapter= ArrayAdapter.createFromResource(this,
                        R.array._CSE32Courses, android.R.layout.simple_spinner_item);
                break;
            default:

                break;


        }


        return adapter;
    }*/

    /*    public void SET_SPINNER(){
            Toast.makeText(getApplicationContext(),"SETspinner called "+" cc "+CourseCode,Toast.LENGTH_SHORT).show();


            Toast.makeText(getApplicationContext(),"After SETspinner called "+" cc "+CourseCode,Toast.LENGTH_SHORT).show();

        }*/
    public String make_heading(String dept, String track, String day) {
        String str = "\n" + dept + " Level: ";

        switch (track.charAt(0)) {
            case '1':
                str += "1";
                break;
            case '2':
                str += "2";
                break;

            case '3':
                str += "3";
                break;
            case '4':
                str += "4";
                break;
            default:
                break;
        }
        str += " Semester: ";
        if (track.charAt(1) == '1') str += "I";
        else str += "II\n" + "Schedule for " + day + "\n";
        return str;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.OK10:
                SET_COURSE(DAY, "10-11", CourseCode1,_A10t);
                break;
            case R.id.OK11:
                SET_COURSE(DAY, "11-12", CourseCode2,_A11t);
                break;
            case R.id.OK12:
                SET_COURSE(DAY, "12-13", CourseCode3,_A12t);
                break;
            case R.id.OK14:
                SET_COURSE(DAY, "14-15", CourseCode4,_A14t);
                break;
            case R.id.OK15:
                SET_COURSE(DAY, "15-16", CourseCode5,_A15t);
                break;
            case R.id.OK16:
                SET_COURSE(DAY, "16-17", CourseCode6,_A16t);
                break;


            default:
                break;


        }

    }
}
