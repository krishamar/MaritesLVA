package com.example.mariteslva;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView messages;
    EditText userMessageInput;
    List<ResponseMessage> messageList;
    MessageAdapter messageAdapter;

    // FloatingActionButton sendMessage;
   // FloatingActionButton voiceMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messages =findViewById(R.id.containerChats);
        userMessageInput = findViewById(R.id.composeMessage);
        messageList = new ArrayList<>();

        messageAdapter = new MessageAdapter(messageList);
        messages.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        messages.setAdapter(messageAdapter);

        userMessageInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND){
                    ResponseMessage messages = new ResponseMessage(userMessageInput.getText().toString(),true);
                    messageList.add(messages);

                    ResponseMessage messages2 = new ResponseMessage(userMessageInput.getText().toString(),false);
                    messageAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });


    }
}