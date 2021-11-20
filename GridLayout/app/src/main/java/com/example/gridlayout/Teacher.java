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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Teacher extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private TextView tnav_headertvName,tnav_headertvEmail,_courses;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private String userId,name,email;
    private AlertDialog.Builder alertdialogbuilder;
    String dept;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);


        View headerView=navigationView.getHeaderView(0);

        tnav_headertvName=headerView.findViewById(R.id.nav_headertvNameID);
        tnav_headertvEmail=headerView.findViewById(R.id.nav_headertvEmailID);





        mAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();

        userId=mAuth.getCurrentUser().getUid();

        Intent intent=getIntent();
        dept=intent.getStringExtra("dept");

        //Toast.makeText(getApplicationContext(),"Teacher - "+ dept, Toast.LENGTH_SHORT).show();


        mFirestore.collection(dept).document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


                name=documentSnapshot.getString("NAME");
                email=documentSnapshot.getString("EMAIL");
                //  String courses=documentSnapshot.getString("COURSES");


                //    email=email.replaceFirst("teacher.","");

                tnav_headertvName.setText(name);
                tnav_headertvEmail.setText(email);
                // tnav_headertvEmail.setText(email);

            }
        });




        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Fragment teacher=new TProfileFragment();
        FragmentTransaction ft0=getSupportFragmentManager().beginTransaction();

        Bundle bundle0= new Bundle();
        bundle0.putString("DEPT",dept);

        teacher.setArguments(bundle0);


        ft0.replace(R.id.fragment_container,teacher).commit();

        ///first e eta select hbe
        // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TProfileFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_profile);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()){

                    case R.id.nav_profile:

                        Fragment teacher=new TProfileFragment();
                        FragmentTransaction ft0=getSupportFragmentManager().beginTransaction();

                        Bundle bundle0= new Bundle();
                        bundle0.putString("DEPT",dept);

                        teacher.setArguments(bundle0);


                        ft0.replace(R.id.fragment_container,teacher).commit();

                        // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TProfileFragment()).commit();

                        break;

                    case R.id.nav_schedule:

                        Fragment sch=new Course_list_Fragment();
                        FragmentTransaction ft2=getSupportFragmentManager().beginTransaction();

                        Bundle bundle2= new Bundle();
                        bundle2.putString("DEPT",dept);
                        bundle2.putString("userID",userId);
                        bundle2.putString("from","schedule");
                        bundle2.putString("tname",name);

                        sch.setArguments(bundle2);


                        ft2.replace(R.id.fragment_container,sch).commit();


                        break;




                    case R.id.nav_post:

                        Fragment post=new Course_list_Fragment();
                        FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();

                        Bundle bundle1= new Bundle();
                        bundle1.putString("DEPT",dept);
                        bundle1.putString("userID",userId);
                        bundle1.putString("tname",name);
                        bundle1.putString("from","post");

                        post.setArguments(bundle1);


                        ft1.replace(R.id.fragment_container,post).commit();


                        break;

                    case R.id.nav_signout:


                        alertdialogbuilder=new AlertDialog.Builder(Teacher.this);
                        alertdialogbuilder.setTitle(R.string.title_text);
                        alertdialogbuilder.setMessage(R.string.Signout_message_text);
                        alertdialogbuilder.setIcon(R.drawable.alerticon);

                        alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

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
}


