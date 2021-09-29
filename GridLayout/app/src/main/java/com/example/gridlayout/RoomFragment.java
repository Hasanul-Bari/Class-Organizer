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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;
import java.text.DateFormat;


import static androidx.constraintlayout.widget.Constraints.TAG;


public class RoomFragment extends Fragment implements View.OnClickListener {

    TextView _R1, _R2, _R3, _R4, _R5, _R6, _R7, _R8, _R9, _R10;  //Room no buttons
    Button _R1V, _R2V, _R3V, _R4V, _R5V, _R6V, _R7V, _R8V, _R9V, _R10V;  //Room Vacant buttons
    Button _R1C, _R2C, _R3C, _R4C, _R5C, _R6C, _R7C, _R8C, _R9C, _R10C;  //Room book buttons
    TextView _R1S, _R2S, _R3S, _R4S, _R5S, _R6S, _R7S, _R8S, _R9S, _R10S, _F;  //Room status
    String Floor, CR, Room, dept = "CSE", level, semester, FL;
    String status1;
    boolean flag = false,AR=false;
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userId;


    public RoomFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_room, container, false);


        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        // userId=mAuth.getCurrentUser().getUid();


        Bundle FLOOR = getArguments();
        if (FLOOR != null) {
            Floor = FLOOR.getString("Floor");
            CR = FLOOR.getString("CR");
            dept = FLOOR.getString("DEPT");
            level = FLOOR.getString("LEVEL");
            semester = FLOOR.getString("SEM");
        }
        dept = dept + " L: " + level + " S: " + semester;


        mFirestore.collection("AR").document(dept).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String ROOMNO=documentSnapshot.getString("room");
                Toast.makeText(getActivity(), "Room "+ROOMNO, Toast.LENGTH_SHORT).show();
                if(ROOMNO.length()>0)AR=true;

            }
        });




        char str = Floor.charAt(0);


        if (str == 'G') {
            Room = "1";
            FL = "F0";
        } else if (str == '1') {
            Room = "2";
            FL = "F1";
        } else if (str == '2') {
            Room = "3";
            FL = "F2";
        } else if (str == '3') {
            Room = "4";
            FL = "F3";
        } else if (str == '4') {
            Room = "5";
            FL = "F4";
        }


        // refresh.setOnClickListener( this);
        //floor no

        _F = view.findViewById(R.id.FloorNo);
        _F.setText(Floor);


        ////Room no text views


        _R1 = view.findViewById(R.id.R1);
        _R2 = view.findViewById(R.id.R2);
        _R3 = view.findViewById(R.id.R3);
        _R4 = view.findViewById(R.id.R4);
        _R5 = view.findViewById(R.id.R5);
        _R6 = view.findViewById(R.id.R6);
        _R7 = view.findViewById(R.id.R7);
        _R8 = view.findViewById(R.id.R8);
        _R9 = view.findViewById(R.id.R9);
        _R10 = view.findViewById(R.id.R10);


        ////Room status text views

        _R1S = view.findViewById(R.id.R1S);
        _R2S = view.findViewById(R.id.R2S);
        _R3S = view.findViewById(R.id.R3S);
        _R4S = view.findViewById(R.id.R4S);
        _R5S = view.findViewById(R.id.R5S);
        _R6S = view.findViewById(R.id.R6S);
        _R7S = view.findViewById(R.id.R7S);
        _R8S = view.findViewById(R.id.R8S);
        _R9S = view.findViewById(R.id.R9S);
        _R10S = view.findViewById(R.id.R10S);


        //////room vacant buttons

        _R1V = view.findViewById(R.id.R1V);
        _R2V = view.findViewById(R.id.R2V);
        _R3V = view.findViewById(R.id.R3V);
        _R4V = view.findViewById(R.id.R4V);
        _R5V = view.findViewById(R.id.R5V);
        _R6V = view.findViewById(R.id.R6V);
        _R7V = view.findViewById(R.id.R7V);
        _R8V = view.findViewById(R.id.R8V);
        _R9V = view.findViewById(R.id.R9V);
        _R10V = view.findViewById(R.id.R10V);

        _R1V.setOnClickListener(this);
        _R2V.setOnClickListener(this);
        _R3V.setOnClickListener(this);
        _R4V.setOnClickListener(this);
        _R5V.setOnClickListener(this);
        _R6V.setOnClickListener(this);
        _R7V.setOnClickListener(this);
        _R8V.setOnClickListener(this);
        _R9V.setOnClickListener(this);
        _R10V.setOnClickListener(this);

        /////room book buttons
        _R1C = view.findViewById(R.id.R1C);
        _R2C = view.findViewById(R.id.R2C);
        _R3C = view.findViewById(R.id.R3C);
        _R4C = view.findViewById(R.id.R4C);
        _R5C = view.findViewById(R.id.R5C);
        _R6C = view.findViewById(R.id.R6C);
        _R7C = view.findViewById(R.id.R7C);
        _R8C = view.findViewById(R.id.R8C);
        _R9C = view.findViewById(R.id.R9C);
        _R10C = view.findViewById(R.id.R10C);

        _R1C.setOnClickListener(this);
        _R2C.setOnClickListener(this);
        _R3C.setOnClickListener(this);
        _R4C.setOnClickListener(this);
        _R5C.setOnClickListener(this);
        _R6C.setOnClickListener(this);
        _R7C.setOnClickListener(this);
        _R8C.setOnClickListener(this);
        _R9C.setOnClickListener(this);
        _R10C.setOnClickListener(this);

        // Toast.makeText(getActivity(), "CR - - "+CR, Toast.LENGTH_SHORT).show();


        String str1[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10"};
        int i = 0;
        _R1.setText(Room + str1[i++]);
        _R2.setText(Room + str1[i++]);
        _R3.setText(Room + str1[i++]);
        _R4.setText(Room + str1[i++]);
        _R5.setText(Room + str1[i++]);
        _R6.setText(Room + str1[i++]);
        _R7.setText(Room + str1[i++]);
        _R8.setText(Room + str1[i++]);
        _R9.setText(Room + str1[i++]);
        _R10.setText(Room + str1[i++]);


        if (CR.charAt(0) == 'Y') {
            flag = true;
/*            Toast.makeText(getActivity(), "Inside ", Toast.LENGTH_SHORT).show();
            onButton(_R1C,_R1V);
            onButton(_R2C,_R2V);
            onButton(_R3C,_R3V);
            onButton(_R4C,_R4V);
            onButton(_R5C,_R5V);
            onButton(_R6C,_R6V);
            onButton(_R7C,_R7V);
            onButton(_R8C,_R8V);
            onButton(_R9C,_R9V);
            onButton(_R10C,_R10V);*/
        } else {
            offButton(_R1C, _R1V);
            offButton(_R2C, _R2V);
            offButton(_R3C, _R3V);
            offButton(_R4C, _R4V);
            offButton(_R5C, _R5V);
            offButton(_R6C, _R6V);
            offButton(_R7C, _R7V);
            offButton(_R8C, _R8V);
            offButton(_R9C, _R9V);
            offButton(_R10C, _R10V);

        }

        ///setting status


        mFirestore.collection(FL).document("R1").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("status");
                _R1S.setText(status);
                if (flag) {
                    status = status.trim();
                    boolean f = status.equals("VACANT");
                    boolean f1 = status.equals(dept);

                    //    Toast.makeText(getActivity(), "entered + "+status, Toast.LENGTH_SHORT).show();

                    if (f) {
                        _R1V.setVisibility(View.INVISIBLE);
                    } else if (f1) {
                        //cr er nijer dept oi room book kore ache
                        confirmOffvacantOn(_R1C, _R1V);
                    } else {
                        //    Toast.makeText(getActivity(), "3rd condition + "+flag+FL, Toast.LENGTH_SHORT).show();
                        offButton(_R1C, _R1V);
                    }
                }


            }

        });

        mFirestore.collection(FL).document("R2").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("status");
                _R2S.setText(status);
                // Toast.makeText(getActivity(), "entered + "+status+FL, Toast.LENGTH_SHORT).show();
                if (flag) {
                    status = status.trim();
                    boolean f = status.equals("VACANT");
                    boolean f1 = status.equals(dept);

                    //  Toast.makeText(getActivity(), "entered + "+status, Toast.LENGTH_SHORT).show();

                    if (f) {
                        _R2V.setVisibility(View.INVISIBLE);
                    } else if (f1) {
                        //cr er nijer dept oi room book kore ache
                        confirmOffvacantOn(_R2C, _R2V);
                    } else {
                        //   Toast.makeText(getActivity(), "3rd condition + "+flag+FL, Toast.LENGTH_SHORT).show();
                        offButton(_R2C, _R2V);
                    }
                }

            }

        });

        mFirestore.collection(FL).document("R3").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("status");
                _R3S.setText(status);
                // Toast.makeText(getActivity(), "entered + "+status+FL, Toast.LENGTH_SHORT).show();
                if (flag) {
                    status = status.trim();
                    boolean f = status.equals("VACANT");
                    boolean f1 = status.equals(dept);

                    // Toast.makeText(getActivity(), "entered + "+status, Toast.LENGTH_SHORT).show();

                    if (f) {
                        _R3V.setVisibility(View.INVISIBLE);
                    } else if (f1) {
                        //cr er nijer dept oi room book kore ache
                        confirmOffvacantOn(_R3C, _R3V);
                    } else {
                        // Toast.makeText(getActivity(), "3rd condition + "+flag+FL, Toast.LENGTH_SHORT).show();
                        offButton(_R3C, _R3V);
                    }
                }

            }

        });

        mFirestore.collection(FL).document("R4").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("status");
                _R4S.setText(status);
                //Toast.makeText(getActivity(), "entered + "+status+FL, Toast.LENGTH_SHORT).show();
                if (flag) {
                    status = status.trim();
                    boolean f = status.equals("VACANT");
                    boolean f1 = status.equals(dept);

                    //   Toast.makeText(getActivity(), "entered + "+status, Toast.LENGTH_SHORT).show();

                    if (f) {
                        _R4V.setVisibility(View.INVISIBLE);
                    } else if (f1) {
                        //cr er nijer dept oi room book kore ache
                        confirmOffvacantOn(_R4C, _R4V);
                    } else {
                        //   Toast.makeText(getActivity(), "3rd condition + "+flag+FL, Toast.LENGTH_SHORT).show();
                        offButton(_R4C, _R4V);
                    }
                }

            }

        });

        mFirestore.collection(FL).document("R5").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("status");
                _R5S.setText(status);
                //Toast.makeText(getActivity(), "entered + "+status+FL, Toast.LENGTH_SHORT).show();
                if (flag) {
                    status = status.trim();
                    boolean f = status.equals("VACANT");
                    boolean f1 = status.equals(dept);

                    //Toast.makeText(getActivity(), "entered + "+status, Toast.LENGTH_SHORT).show();

                    if (f) {
                        _R5V.setVisibility(View.INVISIBLE);
                    } else if (f1) {
                        //cr er nijer dept oi room book kore ache
                        confirmOffvacantOn(_R5C, _R5V);
                    } else {
                        //    Toast.makeText(getActivity(), "3rd condition + "+flag+FL, Toast.LENGTH_SHORT).show();
                        offButton(_R5C, _R5V);
                    }
                }

            }

        });

        mFirestore.collection(FL).document("R6").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("status");
                _R6S.setText(status);
                // Toast.makeText(getActivity(), "entered + "+status+FL, Toast.LENGTH_SHORT).show();
                if (flag) {
                    status = status.trim();
                    boolean f = status.equals("VACANT");
                    boolean f1 = status.equals(dept);

                    // Toast.makeText(getActivity(), "entered + "+status, Toast.LENGTH_SHORT).show();

                    if (f) {
                        _R6V.setVisibility(View.INVISIBLE);
                    } else if (f1) {
                        //cr er nijer dept oi room book kore ache
                        confirmOffvacantOn(_R6C, _R6V);
                    } else {
                        //    Toast.makeText(getActivity(), "3rd condition + "+flag+FL, Toast.LENGTH_SHORT).show();
                        offButton(_R6C, _R6V);
                    }
                }

            }

        });


        mFirestore.collection(FL).document("R7").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("status");
                _R7S.setText(status);
                //Toast.makeText(getActivity(), "entered + "+status+FL, Toast.LENGTH_SHORT).show();
                if (flag) {
                    status = status.trim();
                    boolean f = status.equals("VACANT");
                    boolean f1 = status.equals(dept);

                    //Toast.makeText(getActivity(), "entered + "+status, Toast.LENGTH_SHORT).show();

                    if (f) {
                        _R7V.setVisibility(View.INVISIBLE);
                    } else if (f1) {
                        //cr er nijer dept oi room book kore ache
                        confirmOffvacantOn(_R7C, _R7V);
                    } else {
                        // Toast.makeText(getActivity(), "3rd condition + "+flag+FL, Toast.LENGTH_SHORT).show();
                        offButton(_R7C, _R7V);
                    }
                }

            }

        });


        mFirestore.collection(FL).document("R8").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("status");
                _R8S.setText(status);
                // Toast.makeText(getActivity(), "entered + "+status+FL, Toast.LENGTH_SHORT).show();
                if (flag) {
                    status = status.trim();
                    boolean f = status.equals("VACANT");
                    boolean f1 = status.equals(dept);

                    //Toast.makeText(getActivity(), "entered + "+status, Toast.LENGTH_SHORT).show();

                    if (f) {
                        _R8V.setVisibility(View.INVISIBLE);
                    } else if (f1) {
                        //cr er nijer dept oi room book kore ache
                        confirmOffvacantOn(_R8C, _R8V);
                    } else {
                        //  Toast.makeText(getActivity(), "3rd condition + "+flag+FL, Toast.LENGTH_SHORT).show();
                        offButton(_R8C, _R8V);
                    }
                }

            }

        });


        mFirestore.collection(FL).document("R9").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("status");
                _R9S.setText(status);
                // Toast.makeText(getActivity(), "entered + "+status+FL, Toast.LENGTH_SHORT).show();
                if (flag) {
                    status = status.trim();
                    boolean f = status.equals("VACANT");
                    boolean f1 = status.equals(dept);

                    //   Toast.makeText(getActivity(), "entered + "+status, Toast.LENGTH_SHORT).show();

                    if (f) {
                        _R9V.setVisibility(View.INVISIBLE);
                    } else if (f1) {
                        //cr er nijer dept oi room book kore ache
                        confirmOffvacantOn(_R9C, _R9V);
                    } else {
                        //  Toast.makeText(getActivity(), "3rd condition + "+flag+FL, Toast.LENGTH_SHORT).show();
                        offButton(_R9C, _R9V);
                    }
                }

            }

        });


        mFirestore.collection(FL).document("R10").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("status");
                _R10S.setText(status);
                //  Toast.makeText(getActivity(), "entered + "+status+FL, Toast.LENGTH_SHORT).show();
                if (flag) {
                    status = status.trim();
                    boolean f = status.equals("VACANT");
                    boolean f1 = status.equals(dept);

                    // Toast.makeText(getActivity(), "entered + "+status, Toast.LENGTH_SHORT).show();

                    if (f) {
                        _R10V.setVisibility(View.INVISIBLE);
                    } else if (f1) {
                        //cr er nijer dept oi room book kore ache
                        confirmOffvacantOn(_R10C, _R10V);
                    } else {
                        //  Toast.makeText(getActivity(), "3rd condition + "+flag+FL, Toast.LENGTH_SHORT).show();
                        offButton(_R10C, _R10V);
                    }
                }

            }

        });


        /*Solve(FL,"R1",_R1C,_R1V,_R1S);
       Solve(FL,"R2",_R2C,_R2V,_R2S);
        Solve(FL,"R3",_R3C,_R3V,_R3S);
        Solve(FL,"R4",_R4C,_R4V,_R4S);
        Solve(FL,"R5",_R5C,_R5V,_R5S);
        Solve(FL,"R6",_R6C,_R6V,_R6S);
        Solve(FL,"R7",_R7C,_R7V,_R7S);
        Solve(FL,"R8",_R8C,_R8V,_R8S);
        Solve(FL,"R9",_R9C,_R9V,_R9S);
        Solve(FL,"R10",_R10C,_R10V,_R10S);*/

        //Toast.makeText(getActivity(), "=="+status1, Toast.LENGTH_SHORT).show();


        return view;

    }


    public void offButton(Button x, Button y) {

        x.setVisibility(View.INVISIBLE);
        y.setVisibility(View.INVISIBLE);

    }

    public void confirmOffvacantOn(Button bt1, Button bt2) {
        bt1.setVisibility(View.INVISIBLE);
        bt2.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.R1C:

                if(AR==true)ALERT();
                else Confirm(_R1C, _R1V, _R1S, "R1",Room+"01");
                break;
            case R.id.R1V:
                Vacant(_R1C, _R1V, _R1S, "R1",Room+"01");
                break;
               /* ----------------------------------*/


            case R.id.R2C:
                if(AR==true)ALERT();
                else
                Confirm(_R2C, _R2V, _R2S, "R2",Room+"02");
                break;
            case R.id.R2V:
                Vacant(_R2C, _R2V, _R2S, "R2",Room+"02");
                break;
               /* ----------------------------------*/


            case R.id.R3C:
                if(AR==true)ALERT();
                else
                Confirm(_R3C, _R3V, _R3S, "R3",Room+"03");
                break;
            case R.id.R3V:
                Vacant(_R3C, _R3V, _R3S, "R3",Room+"03");
                break;
               /* ----------------------------------*/


            case R.id.R4C:
                if(AR==true)ALERT();
                else
                Confirm(_R4C, _R4V, _R4S, "R4",Room+"04");
                break;
            case R.id.R4V:
                Vacant(_R4C, _R4V, _R4S, "R4",Room+"04");
                break;
               /* ----------------------------------*/


            case R.id.R5C:
                if(AR==true)ALERT();
                else
                Confirm(_R5C, _R5V, _R5S, "R5",Room+"05");
                break;
            case R.id.R5V:
                Vacant(_R5C, _R5V, _R5S, "R5",Room+"05");
                break;
               /* ----------------------------------*/


            case R.id.R6C:
                if(AR==true)ALERT();
                else
                Confirm(_R6C, _R6V, _R6S, "R6",Room+"06");
                break;
            case R.id.R6V:
                Vacant(_R6C, _R6V, _R6S, "R6",Room+"06");
                break;
               /* ----------------------------------*/


            case R.id.R7C:
                if(AR==true)ALERT();
                else
                Confirm(_R7C, _R7V, _R7S, "R7",Room+"07");
                break;
            case R.id.R7V:
                Vacant(_R7C, _R7V, _R7S, "R7",Room+"07");
                break;
               /* ----------------------------------*/


            case R.id.R8C:
                if(AR==true)ALERT();
                else
                Confirm(_R8C, _R8V, _R8S, "R8",Room+"08");
                break;
            case R.id.R8V:
                Vacant(_R8C, _R8V, _R8S, "R8",Room+"08");
                break;
               /* ----------------------------------*/


            case R.id.R9C:
                if(AR==true)ALERT();
                else
                Confirm(_R9C, _R9V, _R9S, "R9",Room+"09");
                break;
            case R.id.R9V:
                Vacant(_R9C, _R9V, _R9S, "R9",Room+"09");
                break;
               /* ----------------------------------*/


            case R.id.R10C:
                if(AR==true)ALERT();
                else
                Confirm(_R10C, _R10V, _R10S, "R10", Room + "10");
                break;
            case R.id.R10V:
                Vacant(_R10C, _R10V, _R10S, "R10", Room + "10");
                break;
            /* ----------------------------------*/




        }

    }


    public void Confirm(Button bt, Button bt1, TextView status, String R,String RR) {

        status.setText(dept);

        DocumentReference washingtonRef = db.collection(FL).document(R);
        washingtonRef
                .update("status", dept)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        AR=true;
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        Toast.makeText(getActivity(), "Status successfully updated", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                        Toast.makeText(getActivity(), "Status Failed to  updated " + e, Toast.LENGTH_SHORT).show();
                    }
                });

        washingtonRef = db.collection("AR").document(dept);
        washingtonRef
                .update("room", RR)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        Toast.makeText(getActivity(), "Status successfully updated", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                        Toast.makeText(getActivity(), "Status Failed to  updated " + e, Toast.LENGTH_SHORT).show();
                    }
                });





        bt1.setVisibility(View.VISIBLE);
        bt.setVisibility(View.INVISIBLE);


    }

    public void Vacant(Button bt, Button bt1, TextView status, String R,String RR) {
        status.setText("VACANT");

        DocumentReference washingtonRef = db.collection(FL).document(R);
        washingtonRef
                .update("status", "VACANT")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        AR=false;
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        Toast.makeText(getActivity(), "Status successfully updated", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                        Toast.makeText(getActivity(), "Status Failed to  updated " + e, Toast.LENGTH_SHORT).show();
                    }
                });


        washingtonRef = db.collection("AR").document(dept);
        washingtonRef
                .update("room", "")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        Toast.makeText(getActivity(), "Status successfully updated", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                        Toast.makeText(getActivity(), "Status Failed to  updated " + e, Toast.LENGTH_SHORT).show();
                    }
                });

        bt.setVisibility(View.VISIBLE);
        bt1.setVisibility(View.INVISIBLE);

    }
  public void ALERT(){

      AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(getActivity());
      alertdialogbuilder.setTitle("Room Management");
      alertdialogbuilder.setMessage("You already have been \n allocated a room.\n Vacant it first");
      alertdialogbuilder.setIcon(R.drawable.alerticon);



      alertdialogbuilder.setNegativeButton("I under stand", new DialogInterface.OnClickListener() {
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
