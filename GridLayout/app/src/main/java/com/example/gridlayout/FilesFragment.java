package com.example.gridlayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.OpenableColumns;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FilesFragment extends Fragment implements View.OnClickListener{

    String dept,level,semester,fileName,name;
    Uri fileUri;

    private TextView intro,fileNameView;
    private Button selectButton, uploadButton;
    private RecyclerView recyclerView;

    private List<FileItem> fileList;

    DatabaseReference databaseReference;
    StorageReference storageReference;

    ProgressDialog progressDialog;

    private static final int REQUEST_CODE=1;






    public FilesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_files, container, false);


        //Getting data send by intend
        Bundle bd = getArguments();
        if (bd != null) {

            dept = bd.getString("DEPT");
            level = bd.getString("LEVEL");
            semester = bd.getString("SEM");
            name=bd.getString("NAME");
        }

        databaseReference= FirebaseDatabase.getInstance().getReference("Files/"+dept+"_"+level+"_"+semester);
        storageReference= FirebaseStorage.getInstance().getReference("Files/"+dept+"_"+level+"_"+semester);

        intro=view.findViewById(R.id.dept_lev_sem);

        intro.setText(dept+" Department\nlevel: "+level+" Semester: "+semester);

        selectButton=view.findViewById(R.id.selectFileId);
        fileNameView=view.findViewById(R.id.fileNameId);
        uploadButton=view.findViewById(R.id.uploadFileId);

        recyclerView=view.findViewById(R.id.recylerViewId);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fileList=new ArrayList<>();



        uploadButton.setOnClickListener(this);
        selectButton.setOnClickListener(this);

        return view;
    }

    public void onClick(View v){

        if(v.getId()==R.id.selectFileId){
            //Toast.makeText(getActivity(),"Select",Toast.LENGTH_SHORT).show();

            openFileChooser();
        }
        else if(v.getId()==R.id.uploadFileId){
            //Toast.makeText(getActivity(),"Upload",Toast.LENGTH_SHORT).show();

            //check if file is selectet or not
            if(fileUri!=null){
                uploadFile(fileUri,fileName);
            }
            else{
                Toast.makeText(getActivity(),"Please select a file",Toast.LENGTH_SHORT).show();
            }

        }

    }

    //chose file from device
    public void openFileChooser(){

        Intent intent=new Intent();
        intent.setType("*/*");
        String[] mimetypes = {"application/pdf", "image/*", "video/*"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimetypes);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE && resultCode== Activity.RESULT_OK &&  data!=null  && data.getData()!=null){

            fileUri=data.getData();

            Cursor returnCursor = getActivity().getContentResolver().query(fileUri, null, null, null, null);
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();

            fileName=returnCursor.getString(nameIndex);

            fileNameView.setText(fileName);

            //Toast.makeText(getActivity(),FileName,Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(getActivity(),"Please select a file",Toast.LENGTH_SHORT).show();
        }
    }


    // upload file to firebase
    public void uploadFile(Uri fileUri,String fileName){

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading file...");
        progressDialog.setProgress(0);
        progressDialog.show();


        StorageReference ref=storageReference.child(System.currentTimeMillis()+fileName);

        ref.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();

                while(!uriTask.isSuccessful());

                Uri downloadUri=uriTask.getResult();


                //get upload date
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(new Date());

                FileItem fileItem=new FileItem(fileName, downloadUri.toString(),name,date);


                String fileId= databaseReference.push().getKey();

                databaseReference.child(fileId).setValue(fileItem).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(),"File uploaded successfully",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                        else{
                            Toast.makeText(getActivity(),"File upload failed",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"File upload failed",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                int currentProgress=(int) (100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
            }
        });

    }
}