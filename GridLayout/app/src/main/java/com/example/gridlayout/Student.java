package com.example.gridlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;


public class Student extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private TextView snav_headertvName,snav_headertvEmail;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private String userId;
    private AlertDialog.Builder alertdialogbuilder;
     String CR,dept,level,semester;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);

        View headerView=navigationView.getHeaderView(0);

        snav_headertvName=headerView.findViewById(R.id.nav_headertvNameID);
        snav_headertvEmail=headerView.findViewById(R.id.nav_headertvEmailID);




        mAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();

        userId=mAuth.getCurrentUser().getUid();



        mFirestore.collection("STUDENT").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


                String name=documentSnapshot.getString("NAME");
                String email=documentSnapshot.getString("EMAIL");
                     CR=documentSnapshot.getString("CR");
                     dept=documentSnapshot.getString("DEPT");
                     level=documentSnapshot.getString("LEVEL");
                     semester=documentSnapshot.getString("SEM");

                email=email.replaceFirst("student.","");

                snav_headertvName.setText(name);
                snav_headertvEmail.setText(email);

            }
        });






        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        ///first e eta select hbe
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SProfileFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_profile);



/*        MenuItem item=navigationView.findViewById(R.id.nav_room);
       item.setVisi
        this.invalidateOptionsMenu();*/

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()){

                    case R.id.nav_profile:

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SProfileFragment()).commit();

                        break;

                    case R.id.nav_schedule:

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SScheduleFragment()).commit();

                        break;
                    case R.id.nav_room:

                        //passing data to fragment
                        //done by tanver 19 09 2021

                        Fragment FloorF=new Floor_Fragment();
                        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();

                        Bundle bundle= new Bundle();
                        bundle.putString("CR",CR);
                        bundle.putString("DEPT",dept);
                        bundle.putString("LEVEL",level);
                        bundle.putString("SEM",semester);
                        FloorF.setArguments(bundle);


                       ft.replace(R.id.fragment_container,FloorF).commit();

                        break;

                    case R.id.nav_signout:


                        alertdialogbuilder=new AlertDialog.Builder(Student.this);
                        alertdialogbuilder.setTitle(R.string.title_text);
                        alertdialogbuilder.setMessage(R.string.Signout_message_text);
                        alertdialogbuilder.setIcon(R.drawable.alerticon);

                        alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                unsubscribeFromStudent();
                                FirebaseAuth.getInstance().signOut();
                                finish();
                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);

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



                        break;



                }


                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();


    }


    // Hasan : unsunsubscribe FROM Student while signing out

    private void unsubscribeFromStudent(){

        FirebaseMessaging.getInstance().unsubscribeFromTopic("student").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }
}

