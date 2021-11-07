package com.example.gridlayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import 	java.util.Calendar;
import java.util.Locale;
import java.text.DateFormat;


import static androidx.constraintlayout.widget.Constraints.TAG;


public class TScheduleFragment extends Fragment implements View.OnClickListener {


    private TextView day, __10t, __11t, __12t, __14t, __15t, __16t;

    private TextView __10s, __11s, __12s, __14s, __15s, __16s;//for status

    private Button c10, c11, c12, c14, c15, c16;//confirm buttons

    private Button d10, d11, d12, d14, d15, d16, refresh;//decline buttons

    private Button r10, r11, r12, r14, r15, r16;//req buttons

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseFirestore db2 = FirebaseFirestore.getInstance();
    private AlertDialog.Builder alertdialogbuilder;

    private String st10, st11, st12, st14, st15, st16, codetemp = ";";
    private String req10, req11, req12, req14, req15, req16, tm = "",course_title="",dept="",level="",sem="";
    private String previous = "55";
    private String str[] = {"10-11", "11-12", "12-13", "14-15", "15-16", "16-17"};
    String DEPT_RESET,DAY_RESET;


    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore, mFirestore1;
    private String userId = ";";
    private String codeT = ";",slot;
    private String dayOfTheWeek,currentdate;

    private String t1,t2,t3,t4,t5,t6,Ccode1,Ccode2,Ccode3,Ccode4,Ccode5,Ccode6;



    public TScheduleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_tschedule, container, false);


        Bundle bundle = getArguments();
        if (bundle != null) {
            codeT = bundle.getString("Code");
            course_title=bundle.getString("Title");
            dept=bundle.getString("dept");
            level=bundle.getString("lev");
            sem=bundle.getString("sem");

        }





        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        dayOfTheWeek = sdf.format(d);
        day = view.findViewById(R.id.Day);

        __10t = view.findViewById(R.id._10t);
        __11t = view.findViewById(R.id._11t);
        __12t = view.findViewById(R.id._12t);
        __14t = view.findViewById(R.id._14t);
        __15t = view.findViewById(R.id._15t);
        __16t = view.findViewById(R.id._16t);


        __10s = view.findViewById(R.id._10_StatusTV);
        __11s = view.findViewById(R.id._11_StatusTV);
        __12s = view.findViewById(R.id._12_StatusTV);
        __14s = view.findViewById(R.id._14_StatusTV);
        __15s = view.findViewById(R.id._15_StatusTV);
        __16s = view.findViewById(R.id._16_StatusTV);

        c10 = view.findViewById(R.id._10_confirmButton);
        c11 = view.findViewById(R.id._11_confirmButton);
        c12 = view.findViewById(R.id._12_confirmButton);
        c14 = view.findViewById(R.id._14_confirmButton);
        c15 = view.findViewById(R.id._15_confirmButton);
        c16 = view.findViewById(R.id._16_confirmButton);

        d10 = view.findViewById(R.id._10_DeclineButton);
        d11 = view.findViewById(R.id._11_DeclineButton);
        d12 = view.findViewById(R.id._12_DeclineButton);
        d14 = view.findViewById(R.id._14_DeclineButton);
        d15 = view.findViewById(R.id._15_DeclineButton);
        d16 = view.findViewById(R.id._16_DeclineButton);

        r10 = view.findViewById(R.id._10_RequestButton);
        r11 = view.findViewById(R.id._11_RequestButton);
        r12 = view.findViewById(R.id._12_RequestButton);
        r14 = view.findViewById(R.id._14_RequestButton);
        r15 = view.findViewById(R.id._15_RequestButton);
        r16 = view.findViewById(R.id._16_RequestButton);

        //refresh=view.findViewById(R.id.button3);


        c10.setOnClickListener(this);
        c11.setOnClickListener(this);
        c12.setOnClickListener(this);
        c14.setOnClickListener(this);
        c15.setOnClickListener(this);
        c16.setOnClickListener(this);


        r10.setOnClickListener(this);
        r11.setOnClickListener(this);
        r12.setOnClickListener(this);
        r14.setOnClickListener(this);
        r15.setOnClickListener(this);
        r16.setOnClickListener(this);

        d10.setOnClickListener(this);
        d11.setOnClickListener(this);
        d12.setOnClickListener(this);
        d14.setOnClickListener(this);
        d15.setOnClickListener(this);
        d16.setOnClickListener(this);
        // refresh.setOnClickListener( this);


        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        currentdate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());



        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mFirestore1 = FirebaseFirestore.getInstance();

        userId = mAuth.getCurrentUser().getUid();


        //fetching teachers course code
        //which means codeT is teacher's own course code


/*        mFirestore.collection("TEACHER").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                codeT = documentSnapshot.getString("COURSE");
                while (codeT == null) {
                    mFirestore.collection("TEACHER").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            codeT = documentSnapshot.getString("COURSE");
                        }
                    });
                }

                //Toast.makeText(getActivity(), "CODE T___" + codeT, Toast.LENGTH_SHORT).show();
            }
        });*/


        currentdate=dateManipulate(currentdate,currentTime,dayOfTheWeek);
        dayOfTheWeek = dayManipulate(dayOfTheWeek, currentTime);

        dept=dept.trim();
        dept+=level;
        level.trim();
        sem.trim();
        dept+=sem;







        day.setText("Class schedule for\n"+currentdate+"\n"+dayOfTheWeek);

        DEPT_RESET=dept;
        DAY_RESET=dayOfTheWeek;

       dayOfTheWeek=dept+"_"+dayOfTheWeek;


       // Toast.makeText(getActivity(), "DAY "+dayOfTheWeek, Toast.LENGTH_SHORT).show();




       mFirestore.collection(dayOfTheWeek).document("LastRead").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String lastread=documentSnapshot.getString("LastRead");

                //Toast.makeText(getActivity(), lastread+ " lastread", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), currentdate+ " currentdate", Toast.LENGTH_SHORT).show();

                if(currentdate.equals(lastread)==false)
                {


                    //Toast.makeText(getActivity(),  " enter", Toast.LENGTH_SHORT).show();

                    //update lastread

                    DocumentReference washingtonRef = db.collection(dayOfTheWeek).document("LastRead");
                    washingtonRef
                            .update("LastRead", currentdate)
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


                    ///reset data for timeslots

                    SETDATA();

                }


            }


        });


       //newly updated teacher schedule by tanver likhon 16 10 21
        // codeT teacher's own course code
        mFirestore.collection(dayOfTheWeek).document("10-11").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String default_code=documentSnapshot.getString("CODE");
                String current_code=documentSnapshot.getString("CURRENT");
                String status=documentSnapshot.getString("STATUS");
                req10=documentSnapshot.getString("STATUS");

                if (req10.length()>0) {
                    int tmp = req10.indexOf(codeT);
                    if (tmp >= 0) {

                        r10.setText("Requested");

                    }
                }
                st10=status;


                if (default_code.equals(codeT)) {
                    if (status.equals("1") && codeT.equals(current_code)) {
                        d10.setVisibility(View.VISIBLE);
                        offButton(c10, r10);
                    } else if (status.equals("1") && codeT.equals(current_code) == false) {
                        r10.setVisibility(View.VISIBLE);
                        offButton(c10, d10);

                    } else if (status.equals("0") && codeT.equals(current_code) == false) {
                        r10.setVisibility(View.VISIBLE);
                        offButton(c10, d10);
                    } else {
                        c10.setVisibility(View.VISIBLE);
                        d10.setVisibility(View.VISIBLE);
                        r10.setVisibility(View.INVISIBLE);
                    }

                } else if (codeT.equals(current_code)) {
                    d10.setVisibility(View.VISIBLE);
                    offButton(c10, r10);

                } else offButton(c10, d10);

                setTextView(status, __10s, __10t);


                if(current_code.equals("NO CLASS")==false)
                {
                    mFirestore.collection(dept+"_COURSES").document(current_code).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            t2=documentSnapshot.getString("Title");
                            String str=current_code+"\n"+t2;
                            __10t.setText(str);

                        }
                    });
                }
                else
                {
                    String str=current_code;
                    __10t.setText(str);
                }



            }
        });





        mFirestore.collection(dayOfTheWeek).document("11-12").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String default_code=documentSnapshot.getString("CODE");
                String current_code=documentSnapshot.getString("CURRENT");
                String status=documentSnapshot.getString("STATUS");
                req11=documentSnapshot.getString("STATUS");

                if (req11.length()>0) {
                    int tmp = req11.indexOf(codeT);
                    if (tmp >= 0) {

                        r11.setText("Requested");

                    }
                }
                st11=status;


                if (default_code.equals(codeT)) {
                  /*  r11.setVisibility(View.INVISIBLE);
                    c11.setVisibility(View.VISIBLE);
                    d11.setVisibility(View.VISIBLE);*/
                    if (status.equals("1") && codeT.equals(current_code)) {
                        d11.setVisibility(View.VISIBLE);
                        offButton(c11, r11);
                    } else if (status.equals("1") && codeT.equals(current_code) == false) {
                        r11.setVisibility(View.VISIBLE);
                        offButton(c11, d11);

                    } else if (status.equals("0") && codeT.equals(current_code) == false) {
                        r11.setVisibility(View.VISIBLE);
                        offButton(c11, d11);
                    } else {
                        c11.setVisibility(View.VISIBLE);
                        d11.setVisibility(View.VISIBLE);
                        r11.setVisibility(View.INVISIBLE);
                    }

                } else if (codeT.equals(current_code)) {
                    d11.setVisibility(View.VISIBLE);
                    offButton(c11, r11);

                } else offButton(c11, d11);

                setTextView(status, __11s, __11t);


                if(current_code.equals("NO CLASS")==false)
                {
                    mFirestore.collection(dept+"_COURSES").document(current_code).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            t2=documentSnapshot.getString("Title");
                            String str=current_code+"\n"+t2;
                            __11t.setText(str);

                        }
                    });
                }
                else
                {
                    String str=current_code;
                    __11t.setText(str);
                }



            }
        });



        mFirestore.collection(dayOfTheWeek).document("12-13").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String default_code=documentSnapshot.getString("CODE");
                String current_code=documentSnapshot.getString("CURRENT");
                String status=documentSnapshot.getString("STATUS");
                req12=documentSnapshot.getString("STATUS");

                if (req12.length()>0) {
                    int tmp = req12.indexOf(codeT);
                    if (tmp >= 0) {

                        r12.setText("Requested");

                    }
                }
                st12=status;


                if (default_code.equals(codeT)) {
                  /*  r11.setVisibility(View.INVISIBLE);
                    c11.setVisibility(View.VISIBLE);
                    d11.setVisibility(View.VISIBLE);*/
                    if (status.equals("1") && codeT.equals(current_code)) {
                        d12.setVisibility(View.VISIBLE);
                        offButton(c12, r12);
                    } else if (status.equals("1") && codeT.equals(current_code) == false) {
                        r12.setVisibility(View.VISIBLE);
                        offButton(c12, d12);

                    } else if (status.equals("0") && codeT.equals(current_code) == false) {
                        r12.setVisibility(View.VISIBLE);
                        offButton(c12, d12);
                    } else {
                        c12.setVisibility(View.VISIBLE);
                        d12.setVisibility(View.VISIBLE);
                        r12.setVisibility(View.INVISIBLE);
                    }

                } else if (codeT.equals(current_code)) {
                    d12.setVisibility(View.VISIBLE);
                    offButton(c12, r12);

                } else offButton(c12, d12);

                setTextView(status, __12s, __12t);


                if(current_code.equals("NO CLASS")==false)
                {
                    mFirestore.collection(dept+"_COURSES").document(current_code).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            t2=documentSnapshot.getString("Title");
                            String str=current_code+"\n"+t2;
                            __12t.setText(str);

                        }
                    });
                }
                else
                {
                    String str=current_code;
                    __12t.setText(str);
                }



            }
        });


        mFirestore.collection(dayOfTheWeek).document("14-15").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String default_code=documentSnapshot.getString("CODE");
                String current_code=documentSnapshot.getString("CURRENT");
                String status=documentSnapshot.getString("STATUS");
                req14=documentSnapshot.getString("STATUS");

                if (req14.length()>0) {
                    int tmp = req14.indexOf(codeT);
                    if (tmp >= 0) {

                        r14.setText("Requested");

                    }
                }
                st14=status;


                if (default_code.equals(codeT)) {
                  /*  r11.setVisibility(View.INVISIBLE);
                    c11.setVisibility(View.VISIBLE);
                    d11.setVisibility(View.VISIBLE);*/
                    if (status.equals("1") && codeT.equals(current_code)) {
                        d14.setVisibility(View.VISIBLE);
                        offButton(c14, r14);
                    } else if (status.equals("1") && codeT.equals(current_code) == false) {
                        r14.setVisibility(View.VISIBLE);
                        offButton(c14, d14);

                    } else if (status.equals("0") && codeT.equals(current_code) == false) {
                        r14.setVisibility(View.VISIBLE);
                        offButton(c14, d14);
                    } else {
                        c14.setVisibility(View.VISIBLE);
                        d14.setVisibility(View.VISIBLE);
                        r14.setVisibility(View.INVISIBLE);
                    }

                } else if (codeT.equals(current_code)) {
                    d14.setVisibility(View.VISIBLE);
                    offButton(c14, r14);

                } else offButton(c14, d14);

                setTextView(status, __14s, __14t);


                if(current_code.equals("NO CLASS")==false)
                {
                    mFirestore.collection(dept+"_COURSES").document(current_code).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            t2=documentSnapshot.getString("Title");
                            String str=current_code+"\n"+t2;
                            __14t.setText(str);

                        }
                    });
                }
                else
                {
                    String str=current_code;
                    __14t.setText(str);
                }



            }
        });


        mFirestore.collection(dayOfTheWeek).document("15-16").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String default_code=documentSnapshot.getString("CODE");
                String current_code=documentSnapshot.getString("CURRENT");
                String status=documentSnapshot.getString("STATUS");
                req15=documentSnapshot.getString("STATUS");

                if (req15.length()>0) {
                    int tmp = req15.indexOf(codeT);
                    if (tmp >= 0) {

                        r15.setText("Requested");

                    }
                }
                st15=status;


                if (default_code.equals(codeT)) {
                  /*  r11.setVisibility(View.INVISIBLE);
                    c11.setVisibility(View.VISIBLE);
                    d11.setVisibility(View.VISIBLE);*/
                    if (status.equals("1") && codeT.equals(current_code)) {
                        d15.setVisibility(View.VISIBLE);
                        offButton(c15, r15);
                    } else if (status.equals("1") && codeT.equals(current_code) == false) {
                        r15.setVisibility(View.VISIBLE);
                        offButton(c15, d15);

                    } else if (status.equals("0") && codeT.equals(current_code) == false) {
                        r15.setVisibility(View.VISIBLE);
                        offButton(c15, d15);
                    } else {
                        c15.setVisibility(View.VISIBLE);
                        d15.setVisibility(View.VISIBLE);
                        r15.setVisibility(View.INVISIBLE);
                    }

                } else if (codeT.equals(current_code)) {
                    d15.setVisibility(View.VISIBLE);
                    offButton(c15, r15);

                } else offButton(c15, d15);

                setTextView(status, __15s, __15t);


                if(current_code.equals("NO CLASS")==false)
                {
                    mFirestore.collection(dept+"_COURSES").document(current_code).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            t2=documentSnapshot.getString("Title");
                            String str=current_code+"\n"+t2;
                            __15t.setText(str);

                        }
                    });
                }
                else
                {
                    String str=current_code;
                    __15t.setText(str);
                }



            }
        });


        mFirestore.collection(dayOfTheWeek).document("16-17").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String default_code=documentSnapshot.getString("CODE");
                String current_code=documentSnapshot.getString("CURRENT");
                String status=documentSnapshot.getString("STATUS");
                req16=documentSnapshot.getString("STATUS");

                if (req16.length()>0) {
                    int tmp = req16.indexOf(codeT);
                    if (tmp >= 0) {

                        r16.setText("Requested");

                    }
                }
                st16=status;


                if (default_code.equals(codeT)) {
                  /*  r11.setVisibility(View.INVISIBLE);
                    c11.setVisibility(View.VISIBLE);
                    d11.setVisibility(View.VISIBLE);*/
                    if (status.equals("1") && codeT.equals(current_code)) {
                        d16.setVisibility(View.VISIBLE);
                        offButton(c16, r16);
                    } else if (status.equals("1") && codeT.equals(current_code) == false) {
                        r16.setVisibility(View.VISIBLE);
                        offButton(c16, d16);

                    } else if (status.equals("0") && codeT.equals(current_code) == false) {
                        r16.setVisibility(View.VISIBLE);
                        offButton(c16, d16);
                    } else {
                        c16.setVisibility(View.VISIBLE);
                        d16.setVisibility(View.VISIBLE);
                        r16.setVisibility(View.INVISIBLE);
                    }

                } else if (codeT.equals(current_code)) {
                    d16.setVisibility(View.VISIBLE);
                    offButton(c16, r16);

                } else offButton(c16, d16);

                setTextView(status, __16s, __16t);


                if(current_code.equals("NO CLASS")==false)
                {
                    mFirestore.collection(dept+"_COURSES").document(current_code).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            t2=documentSnapshot.getString("Title");
                            String str=current_code+"\n"+t2;
                            __16t.setText(str);

                        }
                    });
                }
                else
                {
                    String str=current_code;
                    __16t.setText(str);
                }



            }
        });


        return view;

    }

    public String dateManipulate(String d, String t,String day)  {
        String dt = d;

        if(day.equals("Friday") == true)
        {
            try {
                Calendar c = Calendar.getInstance();
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Date date = format.parse(dt);
                c.setTime(date);
                c.add(Calendar.DAY_OF_YEAR, 2);
                dt = format.format(c.getTime());
            } catch (Exception e) {
                return dt;
            }
        }

        else if(day.equals("Saturday") == true)
        {
            try {
                Calendar c = Calendar.getInstance();
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Date date = format.parse(dt);
                c.setTime(date);
                c.add(Calendar.DAY_OF_YEAR, 1);
                dt = format.format(c.getTime());
            } catch (Exception e) {
                return dt;
            }
        }

        else if ((t.charAt(0) == '1' && (t.charAt(1) == '8' || t.charAt(1) == '9')) || t.charAt(0) == '2') {

            try {
                Calendar c = Calendar.getInstance();
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Date date = format.parse(dt);
                c.setTime(date);

                if (day.equals("Thursday") == true)
                    c.add(Calendar.DAY_OF_YEAR, 3);
                else
                    c.add(Calendar.DAY_OF_YEAR, 1);

                dt = format.format(c.getTime());
            } catch (Exception e) {
                return dt;
            }


        }

        return dt;
    }


    public String dayManipulate(String d, String t) {
        String str = d;

        if ((t.charAt(0) == '1' && (t.charAt(1) == '8' || t.charAt(1) == '9')) || t.charAt(0) == '2') {
            if (d.equals("Sunday") == true) {

                ////tracking previous day cleared or not
                //SETDATA("Sunday");
                return "Monday";
            }
            if (d.equals("Monday") == true) {

                //SETDATA("Monday");

                return "Tuesday";
            }
            if (d.equals("Tuesday") == true) {

                //SETDATA("Tuesday");

                return "Wednesday";
            }
            if (d.equals("Wednesday") == true) {

                //SETDATA("Wednesday");

                return "Thursday";
            }
            if (d.equals("Thursday") == true) {

                //SETDATA("Thursday");

                return "Sunday";
            }
        }

        if (d.equals("Friday") == true || d.equals("Saturday") == true) {

            //SETDATA("Thursday");

            return "Sunday";
        }
        return str;
    }

    public void offButton(Button x, Button y) {

        x.setVisibility(View.INVISIBLE);
        y.setVisibility(View.INVISIBLE);

    }


    public void setTextView(String status, TextView tv, TextView tv2) {

        //tv2 for the update title of the main schedule

        if (status.equals("-1")) {
            tv.setText("PENDING");
            tv.setTextColor(Color.rgb(255,70,70));
            //tv.setBackgroundColor(Color.rgb(255,70,70));
        } else if (status.equals("1")) {
            tv.setText("CONFIRMED");
            tv.setTextColor(Color.GREEN);
            //tv.setBackgroundColor(Color.GREEN);

        } else if (status.equals("0")) {

            tv.setText("VACANT");
            tv.setTextColor(Color.GREEN);
            //tv.setBackgroundColor(Color.GREEN);

        }

    }

    public void setTextView2(final String timee, TextView tv, final String stat, TextView tv2) {

        DocumentReference washingtonRef = db.collection(dayOfTheWeek).document(timee);
        washingtonRef
                .update("STATUS", stat)
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

        setTextView(stat, tv, tv2);

    }

    ////functionality for requesting for a slot

    ///  resetting data
    public void SETDATA() {

        //functionality changed 21 10 21, clear function got omitted by tanver likhon

        db.collection(dayOfTheWeek)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                     //slot;
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                slot=document.getId();

                                //Toast.makeText(getContext(), "Slot "+slot, Toast.LENGTH_SHORT).show();


                               // Toast.makeText(getContext(), "Slot "+slot.equals("LastRead"), Toast.LENGTH_SHORT).show();

                                //slot.trim();

                                if(slot.equals("LastRead")==true){
                                    Toast.makeText(getContext(), " Entered true", Toast.LENGTH_SHORT).show();

                                }else{

                                    //Toast.makeText(getContext(), "entered false ", Toast.LENGTH_SHORT).show();


                                   // Toast.makeText(getContext(), "Entered for "+slot, Toast.LENGTH_SHORT).show();

                                    Log.d(TAG, "onComplete: 1"+slot);

                                    clear1(slot);

                                }

                            }
                        } else {

                        }
                    }
                });

    }

    /*public void clear1(String dd, String tt, String course, String stat) {


        DocumentReference washingtonRef = db.collection(dd).document(tt);
        washingtonRef
                .update("CURRENT", course, "REQ", "", "STATUS", stat)
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

    }*/

    //done by tanver
// done by tanver likhon 21 10 2021 for flexible schedule
    public void clear1(String Slot) {
/*        Log.d(TAG, "clear1: "+Slot);
        Log.d(TAG, "clear1---: "+Slot+" "+dayOfTheWeek);*/

        mFirestore1.collection(dayOfTheWeek).document(Slot).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String CODE_ = documentSnapshot.getString("CODE");
                String STAT_="-1";
                if(CODE_.equals("NO CLASS")==true)STAT_="0";

                //reseting schedule


              //  Log.d(TAG, "onComplete: 2"+slot);

                DocumentReference washingtonRef = db2.collection(dayOfTheWeek).document(Slot);
                String finalSTAT_ = STAT_;
                washingtonRef
                        .update("CURRENT", CODE_, "REQ", "", "STATUS", STAT_,"CODE",CODE_)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully updated!---3 "+Slot);

                               // Toast.makeText(getContext(), "UPdated at "+Slot+" _"+CODE_+ finalSTAT_, Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "Error updating document", e);

                            }
                        });
            }
        });





    }


/*

// function got omitted after 21 10 2021 update

    public void clear1(String dd, String tt, String course, String stat) {


        DocumentReference washingtonRef = db.collection(dd).document(tt);
        washingtonRef
                //updated by tanver likhon
                .update("CURRENT", course, "REQ", "", "STATUS", stat,"CODE",course)
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

    }*/






    public void request(final String timee, final TextView sch, String status, final Button bt, final Button decl, final TextView stat) {

//updated  collection path by tanver likhon 15 10 21

        mFirestore.collection(dayOfTheWeek).document(timee).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {



                String req = documentSnapshot.getString("REQ");
                String status = documentSnapshot.getString("STATUS");

                if (status.equals("0")) {

                    DocumentReference washingtonRef = db.collection(dayOfTheWeek).document(timee);
                    washingtonRef
                            .update("STATUS", "1", "CURRENT", codeT)
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
                    bt.setVisibility(View.INVISIBLE);
                    String cr = documentSnapshot.getString("CURRENT");
                    stat.setText("CONFIRMED");

                    //stat.setBackgroundColor(Color.GREEN);
                    stat.setTextColor(Color.GREEN);


                    if(codeT.equals("NO CLASS")==false)
                    {
                        mFirestore.collection(dept+"_COURSES").document(codeT).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String tt=documentSnapshot.getString("Title");
                                String str=codeT+"\n"+tt;
                                sch.setText(str);

                            }
                        });
                    }
                    else{
                        sch.setText(codeT);
                    }


                    decl.setVisibility(View.VISIBLE);
                    return;
                }

                int tmp=-1;

                if (req.length()>0) {
                    tmp = req.indexOf(codeT);
                }

                if (tmp >= 0) {
                    req = req.replace(codeT, "");
                    //Toast.makeText(getActivity(), "Already Requested", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getActivity(), "Press again for request", Toast.LENGTH_SHORT).show();
                    bt.setText("REQUEST");
                } else {
                    req += codeT;
                    bt.setText("REQUESTED");
                }


                tm = req;

                DocumentReference washingtonRef = db.collection(dayOfTheWeek).document(timee);
                washingtonRef
                        .update("REQ", req)
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


            }
        });


    }

    public void confirm(final Button dc, final Button conf, final Button rr) {

        dc.setVisibility(View.VISIBLE);
        conf.setVisibility(View.INVISIBLE);
        //rr.setVisibility(View.VISIBLE);
    }

    public void decline(final Button dc, final Button conf, final Button rr, final String time, final TextView sch, final TextView stat) {

        //  Toast.makeText(getContext(), "Decline called$$$$$$$ " + "14-15", Toast.LENGTH_SHORT).show();

        mFirestore.collection(dayOfTheWeek).document(time).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String req = documentSnapshot.getString("REQ");

                rr.setVisibility(View.VISIBLE);
                offButton(dc, conf);

                if (req.length()>0) {

                    final String tmp = req.substring(0, 7);
                    req = req.replace(tmp, "");

                    DocumentReference washingtonRef = db.collection(dayOfTheWeek).document(time);
                    washingtonRef
                            .update("STATUS", "1", "CURRENT", tmp, "REQ", req)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully updated!");


                                    if(codeT.equals("NO CLASS")==false)
                                    {
                                        mFirestore.collection(dept+"_COURSES").document(tmp).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                String tt=documentSnapshot.getString("Title");
                                                String str=tmp+"\n"+tt;
                                                sch.setText(str);

                                            }
                                        });
                                    }
                                    else{
                                        sch.setText(tmp);
                                    }



                                    stat.setText("Confirmed");

                                    //stat.setBackgroundColor(Color.GREEN);
                                    stat.setTextColor(Color.GREEN);


                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error updating document", e);

                                }
                            });


                } else {

                    rr.setVisibility(View.VISIBLE);
                    DocumentReference washingtonRef = db.collection(dayOfTheWeek).document(time);
                    washingtonRef
                            .update("STATUS", "0", "CURRENT", "NO CLASS", "REQ", "")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                                    sch.setText("NO CLASS");


                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error updating document", e);

                                }
                            });
                    setTextView2(time, stat, "0", sch);
                }


            }
        });


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id._10_confirmButton:

                confirmAlert(d10, c10, r10, "10-11", __10t, __10s);

                /*setTextView2("10-11", __10s, "1", __10t);
                confirm(d10, c10, r10);*/
                break;

            case R.id._10_DeclineButton:

                declineAlert(d10, c10, r10, "10-11", __10t, __10s);

                break;

            case R.id._10_RequestButton:
                requestAlert("10-11", __10t, st10, r10, d10, __10s);

                break;


            case R.id._11_confirmButton:

                confirmAlert(d11, c11, r11, "11-12", __11t, __11s);

                break;

            case R.id._11_DeclineButton:

                declineAlert(d11, c11, r11, "11-12", __11t, __11s);

                break;

            case R.id._11_RequestButton:
                requestAlert("11-12", __11t, st11, r11, d11, __11s);

                break;


            case R.id._12_confirmButton:

                confirmAlert(d12, c12, r12, "12-13", __12t, __12s);
                break;

            case R.id._12_DeclineButton:

                declineAlert(d12, c12, r12, "12-13", __12t, __12s);
                break;

            case R.id._12_RequestButton:
                requestAlert("12-13", __12t, st14, r12, d12, __12s);

                break;


            case R.id._14_confirmButton:
                confirmAlert(d14, c14, r14, "14-15", __14t, __14s);
                break;

            case R.id._14_DeclineButton:

                declineAlert(d14, c14, r14, "14-15", __14t, __14s);
                break;

            case R.id._14_RequestButton:
                requestAlert("14-15", __14t, st14, r14, d14, __14s);
                break;


            case R.id._15_confirmButton:
                confirmAlert(d15, c15, r15, "15-16", __15t, __15s);
                break;

            case R.id._15_DeclineButton:

                declineAlert(d15, c15, r15, "15-16", __15t, __15s);
                break;

            case R.id._15_RequestButton:
                requestAlert("15-16", __15t, st15, r15, d15, __15s);
                break;


            case R.id._16_confirmButton:
                confirmAlert(d16, c16, r16, "16-17", __16t, __16s);
                break;

            case R.id._16_DeclineButton:

                declineAlert(d16, c16, r16, "16-17", __16t, __16s);
                break;

            case R.id._16_RequestButton:
                requestAlert("16-17", __16t, st16, r16, d16, __16s);
                break;


        }

    }


    public void declineAlert(final Button dc, final Button conf, final Button rr, final String time, final TextView sch, final TextView stat){


        alertdialogbuilder=new AlertDialog.Builder(getContext());
        alertdialogbuilder.setTitle(R.string.title_text);
        alertdialogbuilder.setMessage(R.string.Decline_message_text);
        alertdialogbuilder.setIcon(R.drawable.alerticon);

        alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                decline(dc, conf, rr, time, sch, stat);
            }
        });

        alertdialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        alertdialogbuilder.setCancelable(false);

        AlertDialog alertDialog=alertdialogbuilder.create();
        alertDialog.show();

    }



    public void confirmAlert(final Button dc, final Button conf, final Button rr, final String time, final TextView sch, final TextView stat) {

        alertdialogbuilder=new AlertDialog.Builder(getContext());
        alertdialogbuilder.setTitle(R.string.title_text);
        alertdialogbuilder.setMessage(R.string.Confirm_message_text);
        alertdialogbuilder.setIcon(R.drawable.alerticon);

        alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                setTextView2(time, stat, "1", sch);
                confirm(dc, conf, rr);


            }
        });

        alertdialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        alertdialogbuilder.setCancelable(false);

        AlertDialog alertDialog=alertdialogbuilder.create();
        alertDialog.show();

    }

    public void requestAlert(final String time, final TextView sch, final String status, final Button bt, final Button decl, final TextView stat) {

        alertdialogbuilder=new AlertDialog.Builder(getContext());
        alertdialogbuilder.setTitle(R.string.title_text);

        String reqStatus =bt.getText().toString();

        //Toast.makeText(getContext(),"hp = "+reqStatus,Toast.LENGTH_LONG).show();

        if(reqStatus.equals("REQUEST")){
            alertdialogbuilder.setMessage(R.string.Request_message_text1);
        }
        else{
            alertdialogbuilder.setMessage(R.string.Request_message_text2);
        }


        alertdialogbuilder.setIcon(R.drawable.alerticon);

        alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request(time, sch, status, bt, decl, stat);

            }
        });

        alertdialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        alertdialogbuilder.setCancelable(false);

        AlertDialog alertDialog=alertdialogbuilder.create();
        alertDialog.show();

    }

}
