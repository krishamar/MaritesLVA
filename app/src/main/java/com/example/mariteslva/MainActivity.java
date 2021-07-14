package com.example.mariteslva;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    RecyclerView messages;
    EditText userMessageInput;
    FloatingActionButton sendMessage;
    FloatingActionButton voiceMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messages =findViewById(R.id.containerChats);
        userMessageInput = findViewById(R.id.composeMessage);



    }
}