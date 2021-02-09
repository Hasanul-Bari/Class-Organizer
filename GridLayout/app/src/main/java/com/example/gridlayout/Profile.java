package com.example.gridlayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends AppCompatActivity {

    TextView name_sir,course_code;

    FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bundle=getIntent().getExtras();

        name_sir=findViewById(R.id.sir_name);
        course_code=findViewById(R.id.course_name);

        if(bundle!=null){
           // String course=bundle.getString("course_code");
            String usrId=bundle.getString("user");

              operation(usrId);
        }



        mAuth=FirebaseAuth.getInstance();


    }

    private void operation(String usrId) {

        //making profile

        DocumentReference ref=db.collection("TEACHER").document(usrId);
        ref.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        String name=documentSnapshot.getString("NAME");
                        String course=documentSnapshot.getString("COURSE");

                        name_sir.setText(name);
                        course_code.setText(course);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {




                    }
                });



        ///making profile end


        ///activities of selection starts


        ///


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_layout,menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if(item.getItemId()==R.id.signout){

            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }
}
