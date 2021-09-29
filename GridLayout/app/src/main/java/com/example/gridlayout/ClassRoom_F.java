package com.example.gridlayout;

import static androidx.constraintlayout.widget.Constraints.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ClassRoom_F extends AppCompatActivity implements View.OnClickListener {
    Button f0, f1, f2, f3, f4, f_reset;
    private FirebaseFirestore mFirestore, mFirestore1;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_room_f);

        //floor buttons
        f0 = findViewById(R.id.F0);
        f1 = findViewById(R.id.F1);
        f2 = findViewById(R.id.F2);
        f3 = findViewById(R.id.F3);
        f4 = findViewById(R.id.F4);
        f_reset = findViewById(R.id.Reset_F);


        //action listeners
        f0.setOnClickListener(this);
        f1.setOnClickListener(this);
        f2.setOnClickListener(this);
        f3.setOnClickListener(this);
        f4.setOnClickListener(this);
        f_reset.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.F0:
                Intent intent=new Intent(ClassRoom_F.this,Floor0.class);
                intent.putExtra("Floor","F0");
                intent.putExtra("Room","1");
                intent.putExtra("Heading","Ground");
                startActivity(intent);

                //Toast.makeText(getApplicationContext(), "F0 start", Toast.LENGTH_SHORT).show();
               // sendingIntent("1");

            // Toast.makeText(getApplicationContext(), "F0 end", Toast.LENGTH_SHORT).show();
                break;
            case R.id.F1:
               // Toast.makeText(getApplicationContext(), "F1", Toast.LENGTH_SHORT).show();
                sendingIntent("2","F1","1st");

                break;
            case R.id.F2:
               //Toast.makeText(getApplicationContext(), "F2", Toast.LENGTH_SHORT).show();
                sendingIntent("3","F2","2nd");

                break;
            case R.id.F3:
            // Toast.makeText(getApplicationContext(), "F3", Toast.LENGTH_SHORT).show();
                sendingIntent("4","F3","3rd");

                break;
            case R.id.F4:
             //Toast.makeText(getApplicationContext(), "F4", Toast.LENGTH_SHORT).show();
                sendingIntent("5","F4","4th");

                break;


            case R.id.Reset_F:

                RESET();


                break;

            default:
                break;
        }
    }

    void sendingIntent(String RR , String FF,String H){
        Intent intent=new Intent(ClassRoom_F.this,Floor0.class);
        intent.putExtra("Room",RR);
        intent.putExtra("Floor",FF);
        intent.putExtra("Heading",H);
        startActivity(intent);

    }

    void RESET() {


        String F[] = {"F0","F1","F2","F3","F4"},R[]={"R1","R2","R3","R4","R5","R6","R7","R8","R9","R10"};



        FirebaseFirestore db = FirebaseFirestore.getInstance();

        for(int i=0;i<5;i++){
            for(int j=0;j<10;j++){
                DocumentReference washingtonRef = db.collection(F[i]).document(R[j]);
                washingtonRef
                        .update("status", "VACANT")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully updated!");
                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating document", e);
                                Toast.makeText(getApplicationContext(), "Failed" + e, Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        }










/*        mFirestore.collection(dayOfTheWeek).document("10-11").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String code = documentSnapshot.getString("CURRENT");
                String code1 = documentSnapshot.getString("CODE");
                // String title = documentSnapshot.getString("Title");
                String status = documentSnapshot.getString("STATUS");

            }
        });*/


    }

}