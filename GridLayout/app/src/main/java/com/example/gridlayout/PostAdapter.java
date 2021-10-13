package com.example.gridlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private List<PostItem> postList;
    private String tid;

    public PostAdapter(Context context, List<PostItem> postList, String tid) {
        this.context = context;
        this.postList = postList;
        this.tid = tid;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.post_layout,parent,false);

        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        PostItem postItem=postList.get(position);

        String content=postItem.getTname()+" : "+postItem.getContent();
        String time=postItem.getDate();

        holder.postView.setText(content);
        holder.postTimeView.setText(time);

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        TextView postView,postTimeView,codeView,titleView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            postView=itemView.findViewById(R.id.postContentId);
            postTimeView=itemView.findViewById(R.id.postTimeId);
        }
    }
}
