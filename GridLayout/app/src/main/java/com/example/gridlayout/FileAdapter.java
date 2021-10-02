package com.example.gridlayout;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {

    private Context context;
    private List<FileItem> fileList;


    public FileAdapter(Context context, List<FileItem> fileList) {
        this.context = context;
        this.fileList = fileList;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.fileitem_layout,parent,false);

        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {

        FileItem fileItem= fileList.get(position);
        String details= fileItem.getFileName()+"\nUploaded by "+fileItem.getFileUploader()+"\n"+fileItem.getUploadDate();
        holder.pos= holder.getAdapterPosition();
        holder.fileTextView.setText(details);

    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public class FileViewHolder extends RecyclerView.ViewHolder {

        TextView fileTextView;
        Button downloadFileButton;
        int pos;

        public FileViewHolder(@NonNull View itemView) {
            super(itemView);

            fileTextView=itemView.findViewById(R.id.fileDetailsTVid);
            downloadFileButton=itemView.findViewById(R.id.downloadFileButtonId);

            // download the clicked file
            downloadFileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: "+pos);

                    ProgressDialog progressDialog=new ProgressDialog(context);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setTitle("Downloading file...");
                    progressDialog.setProgress(0);
                    progressDialog.show();

                    FileItem fileItem=fileList.get(pos);

                    File localFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),fileItem.getFileName());

                    StorageReference storageReference= FirebaseStorage.getInstance().getReferenceFromUrl(fileItem.getFileUrl());

                    storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Local temp file has been created
                            //Log.d(TAG, "onSuccess: download success");
                            progressDialog.dismiss();
                            Toast.makeText(context.getApplicationContext(), "File downloaded successfully" ,Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(context.getApplicationContext(), "File download failed" ,Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull FileDownloadTask.TaskSnapshot snapshot) {

                            int currentProgress=(int) (100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                            progressDialog.setProgress(currentProgress);

                        }
                    });
                }
            });
        }


    }





}
