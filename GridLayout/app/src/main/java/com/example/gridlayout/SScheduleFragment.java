package com.example.gridlayout;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class SScheduleFragment extends Fragment {


    private TextView day,__10t,__11t,__12t,__14t,__15t,__16t;

    private TextView __10s,__11s,__12s,__14s,__15s,__16s;//for status

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String currentdate,dayOfTheWeek,courses;

    String  dept, level, semester;

    private String t1,t2,t3,t4,t5,t6,code1,code2,code3,code4,code5,code6;


    public SScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_sschedule, container, false);

        Bundle sch = getArguments();
        if (sch != null) {

            dept = sch.getString("DEPT");
            level = sch.getString("LEVEL");
            semester = sch.getString("SEM");
        }

        if(semester.length()==2){
            semester="2";
        }
        else if(semester.length()==1){
            semester="1";
        }

        dept = dept+level+semester;

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        dayOfTheWeek = sdf.format(d);
        day=view.findViewById(R.id.Day);


        __10t=view.findViewById(R.id._10);
        __11t=view.findViewById(R.id._11);
        __12t=view.findViewById(R.id._12);
        __14t=view.findViewById(R.id._14);
        __15t=view.findViewById(R.id._15);
        __16t=view.findViewById(R.id._16);


        __10s=view.findViewById(R.id._10Stu_StatusTV);
        __11s=view.findViewById(R.id._11Stu_StatusTV);
        __12s=view.findViewById(R.id._12Stu_StatusTV);
        __14s=view.findViewById(R.id._14Stu_StatusTV);
        __15s=view.findViewById(R.id._15Stu_StatusTV);
        __16s=view.findViewById(R.id._16Stu_StatusTV);





        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        currentdate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());





    // Toast.makeText(getContext(),currentTime,Toast.LENGTH_SHORT).show();

        mAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();


        currentdate=dateManipulate(currentdate,currentTime,dayOfTheWeek);
        dayOfTheWeek=dayManipulate(dayOfTheWeek,currentTime);

        day.setText("Class schedule for\n"+currentdate+"\n"+dayOfTheWeek);


        dayOfTheWeek=dept+"_"+dayOfTheWeek;
        courses=dept+"_COURSES";


        mFirestore.collection(dayOfTheWeek).document("LastRead").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String lastread=documentSnapshot.getString("date");

                //Toast.makeText(getActivity(), lastread+ " lastread", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), currentdate+ " currentdate", Toast.LENGTH_SHORT).show();

                if(currentdate.equals(lastread)==false)
                {


                    //Toast.makeText(getActivity(),  " enter", Toast.LENGTH_SHORT).show();

                    //update lastread

                    DocumentReference washingtonRef = db.collection(dayOfTheWeek).document("LastRead");
                    washingtonRef
                            .update("date", currentdate)
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

                    SETDATA(dayOfTheWeek);

                }


            }


        });



        mFirestore.collection(dayOfTheWeek).document("10-11").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                code1=documentSnapshot.getString("CURRENT");
                t1="";
                String status=documentSnapshot.getString("STATUS");

                setTextView(status,__10s);

                if(code1.equals("NO CLASS")==false)
                {
                    mFirestore.collection(courses).document(code1).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                             t1=documentSnapshot.getString("Title");
                            String str=code1+"\n"+t1;
                            __10t.setText(str);

                        }
                    });
                }
                else
                {
                    String str=code1;
                    __10t.setText(str);
                }

            }
        });


        mFirestore.collection(dayOfTheWeek).document("11-12").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                 code2=documentSnapshot.getString("CURRENT");
                 t2="";
                String status=documentSnapshot.getString("STATUS");

                setTextView(status,__11s);

                if(code2.equals("NO CLASS")==false)
                {
                    mFirestore.collection(courses).document(code2).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                             t2=documentSnapshot.getString("Title");

                            String str=code2+"\n"+t2;
                            __11t.setText(str);

                        }
                    });
                }
                else
                {
                    String str=code2;
                    __11t.setText(str);
                }



            }
        });


        mFirestore.collection(dayOfTheWeek).document("12-13").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                 code3=documentSnapshot.getString("CURRENT");
                t3 ="";
                String status=documentSnapshot.getString("STATUS");

                if(code3.equals("NO CLASS")==false)
                {
                    mFirestore.collection(courses).document(code3).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            t3=documentSnapshot.getString("Title");
                            String str=code3+"\n"+t3;
                            __12t.setText(str);

                        }
                    });
                }
                else
                {
                    String str=code3;
                    __12t.setText(str);
                }

                setTextView(status,__12s);



            }
        });



        mFirestore.collection(dayOfTheWeek).document("14-15").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                code4=documentSnapshot.getString("CURRENT");
                t4="";
                String status=documentSnapshot.getString("STATUS");

                if(code4.equals("NO CLASS")==false)
                {
                    mFirestore.collection(courses).document(code4).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            t4=documentSnapshot.getString("Title");

                            String str=code4+"\n"+t4;
                            __14t.setText(str);



                        }
                    });
                }
                else
                {
                    String str=code4;
                    __14t.setText(str);
                }

                setTextView(status,__14s);



            }
        });




        mFirestore.collection(dayOfTheWeek).document("15-16").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                 code5=documentSnapshot.getString("CURRENT");
                t5="";
                String status=documentSnapshot.getString("STATUS");

                if(code5.equals("NO CLASS")==false)
                {
                    mFirestore.collection(courses).document(code5).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            t5=documentSnapshot.getString("Title");

                            String str=code5+"\n"+t5;

                            __15t.setText(str);

                        }
                    });
                }
                else
                {
                    String str=code5;

                    __15t.setText(str);
                }

                setTextView(status,__15s);





            }
        });

        mFirestore.collection(dayOfTheWeek).document("16-17").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                code6=documentSnapshot.getString("CURRENT");
                t6="";
                String status=documentSnapshot.getString("STATUS");

                if(code6.equals("NO CLASS")==false)
                {
                    mFirestore.collection(courses).document(code6).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            t6=documentSnapshot.getString("Title");

                            String str=code6+"\n"+t6;
                            __16t.setText(str);

                        }
                    });
                }
                else
                {
                    String str=code6;
                    __16t.setText(str);
                }

                setTextView(status,__16s);



            }
        });


         return view;
    }




    //function for handling date


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




    public String dayManipulate(String d,String t ){
        String str=d;

        if((t.charAt(0)=='1'&&(t.charAt(1)=='8'||t.charAt(1)=='9'))||t.charAt(0)=='2')
        {
            if(d.equals("Sunday")==true)return "Monday";
            if(d.equals("Monday")==true)return "Tuesday";
            if(d.equals("Tuesday")==true)return "Wednesday";
            if(d.equals("Wednesday")==true)return "Thursday";
            if(d.equals("Thursday")==true)return "Sunday";
        }

        if(d.equals("Friday")==true||d.equals("Saturday")==true)return "Sunday";
        return str;
    }


    public void setTextView(String status,TextView tv)
    {


        if (status.equals("-1")) {
            tv.setText("PENDING");
            //tv.setBackgroundColor(Color.rgb(255,70,70));
            tv.setTextColor(Color.rgb(255,70,70));
        } else if (status.equals("1")) {
            tv.setText("CONFIRMED");
            //tv.setBackgroundColor(Color.GREEN);
            tv.setTextColor(Color.GREEN);

        } else if (status.equals("0")) {

            tv.setText("VACANT");

            tv.setTextColor(Color.GREEN);
            //tv.setBackgroundColor(Color.GREEN);

        }

    }


    public void SETDATA(final String day) {


        //Toast.makeText(getContext(), "set data started", Toast.LENGTH_SHORT).show();


        switch (day) {

            case "Sunday":
                clear1(day, "10-11", "NO CLASS", "0");
                clear1(day, "11-12", "NO CLASS", "0");
                clear1(day, "12-13", "NO CLASS", "0");
                clear1(day, "14-15", "CSE 303", "-1");
                clear1(day, "15-16", "CSE 305", "-1");
                clear1(day, "16-17", "CSE 307", "-1");
                break;
            case "Monday":
                clear1(day, "10-11", "NO CLASS", "0");
                clear1(day, "11-12", "NO CLASS", "0");
                clear1(day, "12-13", "NO CLASS", "0");
                clear1(day, "14-15", "CSE 305", "-1");
                clear1(day, "15-16", "ECE 311", "-1");
                clear1(day, "16-17", "ECN 305", "-1");
                break;
            case "Thursday":
                clear1(day, "10-11", "NO CLASS", "0");
                clear1(day, "11-12", "NO CLASS", "0");
                clear1(day, "12-13", "NO CLASS", "0");
                clear1(day, "14-15", "CSE 307", "-1");
                clear1(day, "15-16", "ECE 311", "-1");
                clear1(day, "16-17", "CSE 303", "-1");
                break;
            case "Tuesday":
                clear1(day, "10-11", "NO CLASS", "0");
                clear1(day, "11-12", "NO CLASS", "0");
                clear1(day, "12-13", "NO CLASS", "0");
                clear1(day, "14-15", "ECE 311", "-1");
                clear1(day, "15-16", "CSE 307", "-1");
                clear1(day, "16-17", "ECN 305", "-1");
                break;
            case "Wednesday":
                clear1(day, "10-11", "NO CLASS", "0");
                clear1(day, "11-12", "NO CLASS", "0");
                clear1(day, "12-13", "NO CLASS", "0");
                clear1(day, "14-15", "CSE 305", "-1");
                clear1(day, "15-16", "CSE 307", "-1");
                clear1(day, "16-17", "NO CLASS", "0");
                break;


        }


    }

    public void clear1(String dd, String tt, String course, String stat) {


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

    }


}
