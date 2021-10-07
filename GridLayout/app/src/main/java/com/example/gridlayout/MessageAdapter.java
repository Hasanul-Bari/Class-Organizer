package com.example.gridlayout;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private Context context;
    private List<MessageItem> messageList;
    private String uid;

    public MessageAdapter(Context context, List<MessageItem> messageList, String uid) {
        this.context = context;
        this.messageList = messageList;
        this.uid = uid;
    }


    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.message_layout,parent,false);

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        MessageItem messageItem=messageList.get(position);
        String msg=messageItem.getSender()+" : "+messageItem.getMessage();
        holder.messageView.setText(msg);

        String msgUid=messageItem.getUid();

        if(msgUid.equals(uid)){
            holder.messageView.setBackgroundResource(R.drawable.round_bg);

        }else{
            holder.messageView.setBackgroundResource(R.drawable.round_bg2);
            Log.d(TAG, "onBindViewHolder: "+uid+" "+msgUid);
        }


    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView messageView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            messageView=itemView.findViewById(R.id.messagesId);

        }
    }
}
