package com.example.mariteslva;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    protected static final int RESULT_SPEECH=1;
    RecyclerView messages;
    EditText userMessageInput;
    List<ResponseMessage> messageList;
    MessageAdapter messageAdapter;
    FloatingActionButton voiceButton;
    PyObject program = null;
    ResponseMessage user;
    ResponseMessage bot;
    TextToSpeech tts;
    Calendar calendar;
    int time;
    String[] exit = {"ingat", "ingat ka", "kitakits", "magkita tayo ulit", "sa susunod ulit", "paalam din",
            "paalam din. balik ka", "salamat", "sana ay magkaroon ka ng magandang araw"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messages =findViewById(R.id.containerChats);
        userMessageInput = findViewById(R.id.composeMessage);
        voiceButton = findViewById(R.id.voiceButton);
        messageList = new ArrayList<>();
        calendar = Calendar.getInstance();

        messageAdapter = new MessageAdapter(messageList, this);
        messages.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        messages.setAdapter(messageAdapter);


        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));
        
        Python py = Python.getInstance();
        program = py.getModule("virtual_assistant");

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
            }
        });

        userMessageInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND){
                    user = new ResponseMessage(userMessageInput.getText().toString(), false);
                    messageList.add(user);

                    PyObject res = program.callAttr("chat", userMessageInput.getText().toString());
                    bot = new ResponseMessage(res.toString(),true);
                    messageList.add(bot);
                    messageAdapter.notifyDataSetChanged();
                    tts.speak(res.toString(), TextToSpeech.QUEUE_FLUSH, null);

                    if (res.toString().contains("pumupunta sa")) {
                        String url = res.toString().replace("pumupunta sa", "");
                        url = url.replaceAll("\\s+","");
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    }

                    for(String word: exit) {
                        if (word.equals(res.toString())) {
                            MainActivity.this.finish();
                            System.exit(0);
                        }
                    }
                }
                return false;
            }
        });

        voiceButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fil-PH");
                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                } catch (ActivityNotFoundException e){
                    Toast.makeText(getApplicationContext(),"!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });

        time = calendar.get(Calendar.HOUR_OF_DAY);
        if (time < 12) {
            bot = new ResponseMessage("Magandang Umaga!", true);
            messageList.add(bot);
            tts.speak("Magandang Umaga!", TextToSpeech.QUEUE_FLUSH, null);
        } else if (time < 18) {
            bot = new ResponseMessage("Magandang Hapon!", true);
            messageList.add(bot);
            tts.speak("Magandang Hapon!", TextToSpeech.QUEUE_FLUSH, null);
        } else {
            bot = new ResponseMessage("Magandang Gabi!", true);
            messageList.add(bot);
            tts.speak("Magandang Gabi!", TextToSpeech.QUEUE_FLUSH, null);
        }
        bot = new ResponseMessage("Si Marites po ito. Paano ko po kayo matutulungan", true);
        messageList.add(bot);
        messageAdapter.notifyDataSetChanged();
        tts.speak("Si Marites po ito. Paano ko po kayo matutulungan", TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_SPEECH && resultCode == RESULT_OK){
            ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            user = new ResponseMessage(text.get(0), false);
            messageList.add(user);

            PyObject res = program.callAttr("chat", text.get(0));
            bot = new ResponseMessage(res.toString(),true);
            messageList.add(bot);
            messageAdapter.notifyDataSetChanged();
            tts.speak(res.toString(), TextToSpeech.QUEUE_FLUSH, null);

            if (res.toString().contains("pumupunta sa")) {
                String url = res.toString().replace("pumupunta sa", "");
                url = url.replaceAll("\\s+","");
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }

            for(String word: exit) {
                if (word.equals(res.toString())) {
                    MainActivity.this.finish();
                    System.exit(0);
                }
            }
        }
    }
}