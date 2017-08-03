package com.example.devandrin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

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
        final ArrayList<MessageContent> msgList = new ArrayList<>();
        final MessageReply msgReplyObj = new MessageReply(); //Simulates a reply message

        final Random rand = new Random();
        int iChance = 0;

        for (int i = 0; i < 15; i++) {
            iChance = rand.nextInt(2) + 1;
            String sTempMessage = msgReplyObj.generateReplyMsg();
            if (iChance == 1) //Simulate a history of your messages
            {
                msgList.add(new MessageContent(true, sTempMessage));
            } else //otherwise, it is a reply from someone else
            {
                msgList.add(new MessageContent(false, sTempMessage));
            }
        }
        final ChatAdapter caObj = new ChatAdapter(getApplicationContext(), msgList);
        final ListView lv = (ListView) findViewById(R.id.chatMsgList_layout);

        lv.setAdapter(caObj);
        lv.setSelection(lv.getAdapter().getCount() - 1); //Set the focus on the last message

        final EditText msgTextField = (EditText) findViewById(R.id.txtMessage); //Messages are typed here
        ImageButton btnSendMsg = (ImageButton) findViewById(R.id.btnSend);

        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String sYourMessage = "";
                if (!(msgTextField.getText().toString().equals(""))) {
                    sYourMessage = msgTextField.getText().toString();
                    MessageContent mcObj = new MessageContent(true, sYourMessage);
                    msgList.add(mcObj);

                    //Simulate a conversation for now
                    int iReplyChance = rand.nextInt(3) + 1; //66% chance for a reply
                    if ((iReplyChance == 1) || (iReplyChance == 2)) {
                        MessageContent mcReplyObj = new MessageContent(false, msgReplyObj.generateReplyMsg());
                        msgList.add(mcReplyObj);
                    }
                    lv.setAdapter(caObj);
                    lv.setSelection(lv.getAdapter().getCount() - 1); //Set the focus on the last message received or sent
                    msgTextField.setText(""); //Clear the message field
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a message to send.", Toast.LENGTH_SHORT).show();
                }
            }
        }); // end of seton click listener

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
