package com.example.devandrin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Initialize();

        //This part is used to send a message to another user
        //This holds the messages sent and received by the user
        final ArrayList<MessageContent> msgList = new ArrayList<MessageContent>();
        final MessageReply msgReplyObj = new MessageReply();
        final EditText msgTextField = (EditText) findViewById(R.id.txtMessage);

        ImageButton btnSendMsg = (ImageButton) findViewById(R.id.btnSend);

        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                String sYourMessage = msgTextField.getText().toString();
                //Generate a temporary reply message for now
                //A real reply message will be implemented
                String sReplyMsg = msgReplyObj.generateReplyMsg();
                msgList.add(new MessageContent(sYourMessage, sReplyMsg));
                ChatAdapter caObj = new ChatAdapter(getApplicationContext(), msgList);
                ListView lv = (ListView) findViewById(R.id.chatMsgList_layout);
                lv.setAdapter(caObj);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void Initialize() {

        Intent i = getIntent();
        setTitle(i.getStringExtra("Name"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
