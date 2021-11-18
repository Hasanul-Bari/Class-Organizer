package com.example.gridlayout;

import static androidx.constraintlayout.widget.Constraints.TAG;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class cse_32 extends AppCompatActivity implements View.OnClickListener {


    TextView _LevelSemester, _DAY1, _DAY2, _DAY3, _DAY4, _DAY5;
    Button _View_Schedule1, _Reset_Schedule1, _View_Schedule2, _Reset_Schedule2, _View_Schedule3, _Reset_Schedule3, _View_Schedule4, _Reset_Schedule4, _View_Schedule5, _Reset_Schedule5;
    String teacher_course,code;
    EditText _CourseCode, _CourseTitle;
    Button _OKCourse;

    Spinner spinner1;

    String dept, level, session, track, day,teacher_name,Teacher_ID,idx_id;
    int idx;
    String[] Slots = {"10-11", "11-12", "12-13", "14-15", "15-16", "16-17"};
    //String[] Days={"_Sunday,_Monday,_Tuesday,_Wednesday,_Thursday"};
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseFirestore db1 = FirebaseFirestore.getInstance();
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        _LevelSemester = findViewById(R.id.LevelHeading);
        //Toast.makeText(getApplicationContext(), "MAIN Schedule- cse32 class", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_cse32);

        _LevelSemester = findViewById(R.id.LevelHeading);

        _View_Schedule1 = findViewById(R.id.View_Schedule1);
        _Reset_Schedule1 = findViewById(R.id.Reset_Schedule1);

        _View_Schedule2 = findViewById(R.id.View_Schedule2);
        _Reset_Schedule2 = findViewById(R.id.Reset_Schedule2);

        _View_Schedule3 = findViewById(R.id.View_Schedule3);
        _Reset_Schedule3 = findViewById(R.id.Reset_Schedule3);


        _View_Schedule4 = findViewById(R.id.View_Schedule4);
        _Reset_Schedule4 = findViewById(R.id.Reset_Schedule4);

        _View_Schedule5 = findViewById(R.id.View_Schedule5);
        _Reset_Schedule5 = findViewById(R.id.Reset_Schedule5);
        spinner1 = findViewById(R.id.teacherDrop);


        _DAY1 = findViewById(R.id.DAY1);

        // day=_DAY.getText().toString();


        _View_Schedule1.setOnClickListener(this);
        _Reset_Schedule1.setOnClickListener(this);

        _View_Schedule2.setOnClickListener(this);
        _Reset_Schedule2.setOnClickListener(this);

        _View_Schedule3.setOnClickListener(this);
        _Reset_Schedule3.setOnClickListener(this);

        _View_Schedule4.setOnClickListener(this);
        _Reset_Schedule4.setOnClickListener(this);

        _View_Schedule5.setOnClickListener(this);
        _Reset_Schedule5.setOnClickListener(this);


        //edittext
        _CourseCode = findViewById(R.id.CourseCode);
        _CourseTitle = findViewById(R.id.CourseTitle);


        _OKCourse = findViewById(R.id.OKCourse);
        _OKCourse.setOnClickListener(this);


        Intent intent = getIntent();
        level = intent.getStringExtra("LevelSemester");
       // session = intent.getStringExtra("Session");
        dept = intent.getStringExtra("Dept");
        track = intent.getStringExtra("Track");


        String L = "Level: ";
        L += track.charAt(0);
        L += " Semester: ";
        if (track.charAt(1) == '1') L += "I\n";
        else L += "II\n";
        Toast.makeText(getApplicationContext(), "CS--" + dept, Toast.LENGTH_SHORT).show();
        // Toast.makeText(getApplicationContext(),_LevelSemester.getText().toString(),Toast.LENGTH_SHORT).show();
       // _LevelSemester.setText(L + " " + dept + " " + session + "\n");
        _LevelSemester.setText(L + " " + dept + " " + "\n");

        ArrayList<String>TeacherList=new ArrayList<String>();
        TeacherList.add("SELECT_TEACHER");

        ArrayList<String>TeacherList_ID=new ArrayList<String>();
        TeacherList_ID.add("SELECT_TEACHER");


        db.collection(dept)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    String name;
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                name=document.getString("NAME");
                                Teacher_ID=document.getId();
                                TeacherList.add(name);
                                TeacherList_ID.add(Teacher_ID);
                                //Toast.makeText(getApplicationContext(),name ,Toast.LENGTH_SHORT).show();ck++;

                            }
                        } else {
                            // Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });






        ArrayAdapter<String> branchListAdapter = new ArrayAdapter<>(cse_32.this, android.R.layout.simple_spinner_item, TeacherList);
        branchListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner1.setAdapter(branchListAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                teacher_name = spinner1.getSelectedItem().toString();

                idx=spinner1.getSelectedItemPosition();//teacher name index in the array list
                //   Toast.makeText(getApplicationContext(), "idx "+idx, Toast.LENGTH_SHORT).show();

                idx_id=TeacherList_ID.get(idx);//teacher name id

                Toast.makeText(getApplicationContext(), "idx "+idx_id, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }





    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {
            case R.id.View_Schedule1:
                intent = new Intent(cse_32.this, Admin_Schedule.class);
                intent.putExtra("Dept", dept);
                intent.putExtra("Track", track);
                intent.putExtra("Day", "Sunday");
                Toast.makeText(getApplicationContext(), "view schcedule" + dept+ track, Toast.LENGTH_SHORT).show();

                startActivity(intent);
                break;
            case R.id.Reset_Schedule1:
                RESET_SCHEDULE("Sunday");
                break;

            case R.id.View_Schedule2:
                intent = new Intent(cse_32.this, Admin_Schedule.class);
                intent.putExtra("Dept", dept);
                intent.putExtra("Track", track);
                intent.putExtra("Day", "Monday");
                startActivity(intent);
                break;
            case R.id.Reset_Schedule2:
                RESET_SCHEDULE("Monday");
                break;
            case R.id.View_Schedule3:
                intent = new Intent(cse_32.this, Admin_Schedule.class);
                intent.putExtra("Dept", dept);
                intent.putExtra("Track", track);
                intent.putExtra("Day", "Tuesday");
                startActivity(intent);
                break;
            case R.id.Reset_Schedule3:
                RESET_SCHEDULE("Tuesday");
                break;
            case R.id.View_Schedule4:
                intent = new Intent(cse_32.this, Admin_Schedule.class);
                intent.putExtra("Dept", dept);
                intent.putExtra("Track", track);
                intent.putExtra("Day", "Wednesday");
                startActivity(intent);
                break;
            case R.id.Reset_Schedule4:
                RESET_SCHEDULE("Wednesday");
                break;
            case R.id.View_Schedule5:
                intent = new Intent(cse_32.this, Admin_Schedule.class);
                intent.putExtra("Dept", dept);
                intent.putExtra("Track", track);
                intent.putExtra("Day", "Thursday");
                startActivity(intent);
                break;
            case R.id.Reset_Schedule5:
                RESET_SCHEDULE("Thursday");
                break;

            case R.id.OKCourse:
                code = _CourseCode.getText().toString();
                code = code.trim();
                String title = _CourseTitle.getText().toString();
                title = title.trim();


                if (code.length() == 0 || title.length() == 0) {

                    Toast.makeText(getApplicationContext(), "Code or Title of the course is empty", Toast.LENGTH_LONG).show();
                }
                else if(teacher_name.equals("SELECT_TEACHER")){
                    Toast.makeText(getApplicationContext(), "SELECT COURSE TEACHER", Toast.LENGTH_LONG).show();

                }


                else {
                    Map<String, Object> profile = new HashMap<>();
                    profile.put("Title", title);
                    profile.put("Teacher", teacher_name);

                    db.collection(dept + track + "_" + "COURSES").document(code).set(profile)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {


                                    Toast.makeText(getApplicationContext(), dept + track + "_" + "COURSES", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), dept+" IDX "+idx_id + code, Toast.LENGTH_SHORT).show();

                                    mFirestore.collection(dept).document(idx_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                                            teacher_course=documentSnapshot.getString("COURSES");
                                            teacher_course+=(code+" "+dept+track+" ");


                                            DocumentReference washingtonRef = db1.collection(dept).document(idx_id);
                                            washingtonRef
                                                    .update("COURSES", teacher_course)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d(TAG, "DocumentSnapshot successfully updated!");
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.w(TAG, "Error updating document", e);
                                                        }
                                                    });


                                            Toast.makeText(getApplicationContext(), "teacher course "+idx_id, Toast.LENGTH_SHORT).show();

                                        }


                                    });



                                    Toast.makeText(getApplicationContext(), "Data Stored Successfully", Toast.LENGTH_SHORT).show();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(getApplicationContext(), "Failed to Store data Try again!", Toast.LENGTH_SHORT).show();

                                }
                            });
                    //fetching teacher's previous courses

/*

                                   mFirestore.collection(dept).document(idx_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                       @Override
                                       public void onSuccess(DocumentSnapshot documentSnapshot) {

                                           teacher_course=documentSnapshot.getString("COURSES");
                                           teacher_course+=(code+" "+dept+track+" ");

                                           DocumentReference washingtonRef = db.collection(dept).document(idx_id);
                                           washingtonRef
                                                   .update("COURSES", teacher_course)
                                                   .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                       @Override
                                                       public void onSuccess(Void aVoid) {
                                                           Log.d(TAG, "DocumentSnapshot successfully updated!");
                                                       }
                                                   })
                                                   .addOnFailureListener(new OnFailureListener() {
                                                       @Override
                                                       public void onFailure(@NonNull Exception e) {
                                                           Log.w(TAG, "Error updating document", e);
                                                       }
                                                   });

                                           Toast.makeText(getApplicationContext(), "teacher course "+idx_id, Toast.LENGTH_SHORT).show();

                                       }


                                   });

*/






                }
                break;//16092021/tanver

            default:

                break;


        }


    }

    public void RESET_SCHEDULE(String dd) {
        Toast.makeText(getApplicationContext(), dept + track, Toast.LENGTH_LONG).show();

        Map<String, Object> profile = new HashMap<>();
        Map<String, Object> profile1 = new HashMap<>();
        profile.put("CODE", "NO CLASS");
        profile.put("CURRENT", "NO CLASS");
        profile.put("REQ", "");
        profile.put("STATUS", "0");
        profile.put("Title", "");

        profile1.put("LastRead", "01/01/1900");

        String str = dept + track + "_" + dd;
        for (int i = 0; i < 6; i++)
            db.collection(str).document(Slots[i]).set(profile)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //Toast.makeText(getApplicationContext(), "Data Stored Successfully", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            //Toast.makeText(getApplicationContext(), "Failed to Store data Try again!", Toast.LENGTH_SHORT).show();

                        }
                    });

        db.collection(str).document("LastRead").set(profile1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Toast.makeText(getApplicationContext(), "Data Stored Successfully", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //Toast.makeText(getApplicationContext(), "Failed to Store data Try again!", Toast.LENGTH_SHORT).show();

                    }
                });

    }
}

