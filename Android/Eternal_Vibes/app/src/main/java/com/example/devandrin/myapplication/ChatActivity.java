package com.example.devandrin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

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
        final ArrayList<MessageContent> msgList = new ArrayList<MessageContent>();
        final MessageReply msgReplyObj = new MessageReply(); //Simulates a reply message

        final Random rand = new Random();
        int iChance = 0;
        for(int i = 0; i < 10; i++)
        {
            iChance = rand.nextInt(2) + 1;
            if(iChance == 1) //Simulate a history of your messages
            {
                msgList.add(new MessageContent(true, "My Previous Message #" + i));
            }
            else //otherwise, it is a reply from someone else
            {
                msgList.add(new MessageContent(false, "Reply Message # " + i));
            }
        }

        final ChatAdapter caObj = new ChatAdapter(getApplicationContext(), msgList);
        final ListView lv = (ListView) findViewById(R.id.chatMsgList_layout);
        lv.setAdapter(caObj);
        lv.setSelection(lv.getAdapter().getCount() - 1); //Set the focus on the last message

        final EditText msgTextField = (EditText) findViewById(R.id.txtMessage);
        ImageButton btnSendMsg = (ImageButton) findViewById(R.id.btnSend);

        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String sYourMessage = msgTextField.getText().toString();
                MessageContent mcObj = new MessageContent(true, sYourMessage);
                msgList.add(mcObj);

                int iReplyChance = rand.nextInt(2) + 1;
                if(iReplyChance == 1)
                {
                    MessageContent mcReplyObj = new MessageContent(false, msgReplyObj.generateReplyMsg());
                    msgList.add(mcReplyObj);
                }
                lv.setAdapter(caObj);
                lv.setSelection(lv.getAdapter().getCount() - 1); //Set the focus on the last message
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
