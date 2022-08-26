package com.example.gridlayout;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class Course_list_Fragment extends Fragment {

    String dept,userID,courses,from,tname;

    //String dpt,title,lvl,sem,class_;

    private List<CourseItem> courseList;
    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;

    private FirebaseFirestore firestore,firestore1;

    public Course_list_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_course_list_, container, false);

        //Getting data send by intend
        Bundle bd = getArguments();
        if (bd != null) {
            dept = bd.getString("DEPT");
            userID=bd.getString("userID");
            from=bd.getString("from");
            tname=bd.getString("tname");
        }

        recyclerView=view.findViewById(R.id.recylerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        courseList=new ArrayList<>();

        firestore=FirebaseFirestore.getInstance();
        firestore1=FirebaseFirestore.getInstance();

        //getting course string

        firestore.collection(dept).document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                courses=documentSnapshot.getString("COURSES");

                //Toast.makeText(getActivity(), courses ,Toast.LENGTH_SHORT).show();





                for(int i=1; i<=courses.length(); i=i+14 )
                {

                    if(i+13<=courses.length()){

                        CourseItem courseItem=new CourseItem();

                        String s=courses.substring(i-1,i+13);
                        String code=s.substring(0,7);
                        String class_=s.substring(8,13);
                        String dpt=class_.substring(0,3);
                        String lvl=class_.substring(3,4);
                        String sem=class_.substring(4,5);




                        firestore1.collection(class_+"_COURSES").document(code).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                String title=documentSnapshot.getString("Title");


                                courseItem.setCourseCode(code);
                                courseItem.setCourseTitle(title);
                                courseItem.setDept(dpt);
                                courseItem.setLevel(lvl);
                                courseItem.setSemester(sem);
                                courseItem.setClass__(class_);

                                courseList.add(courseItem);

                                courseAdapter=new CourseAdapter(getActivity(),courseList);
                                recyclerView.setAdapter(courseAdapter);

                                //Log.d(TAG, "aro vitore: "+courseList.size()+code);
                            }
                        });

                        //Log.d(TAG, "vitore: "+courseList.size());

                    }



                }

                //Log.d(TAG, "onSuccess: "+courseList.size());


            }
        });





        return view;
    }






    // class for containing course item

    public class CourseItem{

        private String courseCode,courseTitle,dept,level,semester,class__;

        public CourseItem(String courseCode, String courseTitle, String dept, String level, String semester, String class__) {
            this.courseCode = courseCode;
            this.courseTitle = courseTitle;
            this.dept = dept;
            this.level = level;
            this.semester = semester;
            this.class__ = class__;
        }

        public CourseItem() {
        }

        public String getCourseCode() {
            return courseCode;
        }

        public void setCourseCode(String courseCode) {
            this.courseCode = courseCode;
        }

        public String getCourseTitle() {
            return courseTitle;
        }

        public void setCourseTitle(String courseTitle) {
            this.courseTitle = courseTitle;
        }

        public String getDept() {
            return dept;
        }

        public void setDept(String dept) {
            this.dept = dept;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getSemester() {
            return semester;
        }

        public void setSemester(String semester) {
            this.semester = semester;
        }

        public String getClass__() {
            return class__;
        }

        public void setClass__(String class__) {
            this.class__ = class__;
        }
    }


    // CourseAdapter class

    public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>{

        private Context context;
        private List<CourseItem> courseItems;

        public CourseAdapter(Context context, List<CourseItem> courseItems) {
            this.context = context;
            this.courseItems = courseItems;
        }

        @NonNull
        @Override
        public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
            View view=layoutInflater.inflate(R.layout.courseitem_layout,parent,false);

            return new CourseViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {

            CourseItem courseItem=courseItems.get(position);

            String sem=courseItem.getSemester();

            if(sem.equals("1")){
                sem="I";
            }
            else if(sem.equals("2")){
                sem="II";
            }

            String class_="Dept: "+courseItem.getDept()+"   Level: "+courseItem.getLevel()+"   Semester: "+sem;

            holder.codeView.setText(courseItem.getCourseCode());
            holder.titleView.setText(courseItem.getCourseTitle());
            holder.classView.setText(class_);

            holder.pos=holder.getAdapterPosition();

        }

        @Override
        public int getItemCount() {
            return courseItems.size();
        }

        public class CourseViewHolder extends RecyclerView.ViewHolder {

            TextView codeView,titleView,classView;
            int pos;

            LinearLayout linearLayout;

            public CourseViewHolder(@NonNull View itemView) {

                super(itemView);

                codeView=itemView.findViewById(R.id.ccodeId);
                titleView=itemView.findViewById(R.id.ctitleId);
                classView=itemView.findViewById(R.id.classID);

                linearLayout=itemView.findViewById(R.id.LinearLayoutId);

                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "onClick: "+pos+from);

                        CourseItem courseItem=courseItems.get(pos);


                        FragmentTransaction fragmentTransaction= getFragmentManager().beginTransaction();
                        fragmentTransaction.addToBackStack(null); // back pressed krle prev fragment e jabe

                        Bundle bundle = new Bundle();
                        bundle.putString("Code", courseItem.getCourseCode());//class code like cse 302
                        bundle.putString("Title",courseItem.getCourseTitle());
                        bundle.putString("Class_",courseItem.getClass__());//cse32
                        bundle.putString("dept",courseItem.getDept());//cse
                        bundle.putString("lev",courseItem.getLevel());//3
                        bundle.putString("sem",courseItem.getSemester());//not in roman
                        bundle.putString("tname",tname);

                        if(from.equals("post")){

                            Fragment post = new PostFragment();
                            post.setArguments(bundle);

                            fragmentTransaction.replace(R.id.fragment_container,post).commit();
                        }
                        else if(from.equals("schedule")){

                            Fragment schedule = new TScheduleFragment();
                            schedule.setArguments(bundle);

                            fragmentTransaction.replace(R.id.fragment_container,schedule).commit();
                        }
                        else if(from.equals("room")){

                            Fragment FloorF=new Floor_Fragment();
                            FragmentTransaction ft2=getFragmentManager().beginTransaction();
                            ft2.addToBackStack(null);

                            Log.d(TAG, "onClick: Entered");

                            Bundle bundle1= new Bundle();
                            bundle1.putString("CR","No");
                            bundle1.putString("DEPT",dept);
                            bundle1.putString("LEVEL",courseItem.getLevel());
                            bundle1.putString("SEM",courseItem.getSemester());
                            FloorF.setArguments(bundle1);

                            fragmentTransaction.replace(R.id.fragment_container,FloorF).commit();

                        }



                    }
                });
            }
        }
    }



}