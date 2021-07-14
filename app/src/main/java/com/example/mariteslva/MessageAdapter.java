package com.example.mariteslva;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// A class used to connect the messages to the Recycle view which is used to
// display the list of messages of the bot and user in a vertical list and
// can it is scrollable. It is also a class used to determine which viewholders
// will be displayed.
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

    //method to check if the user is a bot and return the specific layout
    @Override
    public int getItemViewType(int position) {
        int botMessages = R.layout.container_marites_message;
        int userMessages = R.layout.container_user_messages;

        if(messageList.get(position).isMarites()){
            return botMessages;
        }
        return userMessages;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CustomViewHolder view = new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent,false));
        return view ;

    }

    //a method used to pass the messages to the view holder
    @Override
    public void onBindViewHolder(MessageAdapter.CustomViewHolder holder, int position) {
        holder.textView.setText(messageList.get(position).getMessage());

    }
}
