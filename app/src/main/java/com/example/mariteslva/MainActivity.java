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

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
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

        messageAdapter = new MessageAdapter(messageList, this);
        messages.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        messages.setAdapter(messageAdapter);

        /*
        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));
        
        Python py = Python.getInstance();
        final PyObject prog = py.getModule("virtual_assistant");

         */
        userMessageInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND){
                    ResponseMessage messages = new ResponseMessage(userMessageInput.getText().toString(), false);
                    messageList.add(messages);
                    /*
                    PyObject res = prog.callAttr("chat", userMessageInput.getText().toString());
                    ResponseMessage bot = new ResponseMessage(res.toString(),true);
                    messageList.add(bot);
                    */
                    messageAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
    }
}