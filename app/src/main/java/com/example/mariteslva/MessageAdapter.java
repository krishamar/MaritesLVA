package com.example.mariteslva;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.CustomViewHolder>{

    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public CustomViewHolder(View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.containerMessages);
            //textView = itemView.findViewById(R.id.textmessage);
            }

        }


    List<ResponseMessage> messageList;
    public MessageAdapter(List<ResponseMessage> messageList){
        this.messageList = messageList;

    }

    @Override
    public MessageAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MessageAdapter.CustomViewHolder holder, int position) {

    }

    //method to check if the user is a bot and return the specific layout 
    @Override
    public int getItemViewType(int position) {
        if(messageList.get(position).isMarites()){
            return R.layout.container_marites_message;
        }
        return R.layout.container_user_messages;
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
