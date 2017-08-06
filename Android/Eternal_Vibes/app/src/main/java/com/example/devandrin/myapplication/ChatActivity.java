package com.example.devandrin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Random;

public class ChatActivity extends AppCompatActivity {

    private ArrayList<Chat> chatList = new ArrayList<>();
    private int iCurrentUserID = 0;
    private int iChatID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Initialize();

        final ArrayList<MessageContent> msgList = new ArrayList<>();

        //Gets the current user ID from shared preferences, when onResume is invoked in HomeActivity
        iCurrentUserID = Integer.parseInt(HomeActivity.getInstance().activeuserID);

        //TODO Gotta use the below Array list that contains all messages, see Message class for structure of Message.
        ArrayList<Message> m = HomeActivity.getDbHelper().getMessages(getIntent().getIntExtra("ChatID",-1));

        for(Message msgObj : m){
            //Check if the message was sent by the current user
            //If it is then it is the user's message, else it is someone else's
            if(msgObj.SenderID == iCurrentUserID){
                MessageContent msg = new MessageContent(true, msgObj.Message);
                msgList.add(msg);
            }
            else{
                MessageContent msg = new MessageContent(false, msgObj.Message);
                msgList.add(msg);
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
                    //Make the send message request here

                    //Get the user's message to send and make the request in a new thread
                    sYourMessage = msgTextField.getText().toString();
                    ChatThread ctObj = new ChatThread(sYourMessage, iChatID, iCurrentUserID);
                    ctObj.setDaemon(true); //Destroy this thread when this activity is finished
                    ctObj.start();
                    msgList.add(new MessageContent(true, sYourMessage));
                    MessengerUtil.makeRequest(Integer.toString(iChatID));

                    //Update the activity to display the sent message
                    lv.setAdapter(caObj);
                    caObj.notifyDataSetChanged();
                    lv.setSelection(lv.getAdapter().getCount() - 1);
                    msgTextField.setText("");
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please enter a message to send.", Toast.LENGTH_SHORT).show();
                }


            }
        }); // end of seton click listener

    }

    @Override
    protected void onResume(){
        super.onResume();
        MessengerUtil.makeRequest(Integer.toString(iChatID));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void Initialize() {
        Intent i = getIntent();
        setTitle(i.getStringExtra("Name"));
        iChatID = i.getIntExtra("ChatID", 0);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static void getMessages(int id)
    {
        String url = "https://eternalvibes.me/getmessages/" + id;
        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() >= 0) {
                    ArrayList<Message> messages = Message.fromJSONArray(response);
                    for(Message m : messages)
                    {
                        HomeActivity.getDbHelper().insertMessage(m);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //do nothing
            }
        });
        RequestQueueSingleton.getInstance(HomeActivity.getInstance()).addToQ(jar);
    }
}
