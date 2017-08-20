package com.example.devandrin.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Devandrin on 2017/06/28.
 */

public class DBHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "Testing.db";
    private final static int V = 3;
    public DBHelper(Context c) {
        super(c, DB_NAME, null, V);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContractClass.CREATE_CHAT_TABLE);
        db.execSQL(ContractClass.CREATE_USERS_TABLE);
        db.execSQL(ContractClass.CREATE_MESSAGE_TABLE);
    }
    public void resetData()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(ContractClass.DELETE_MESSAGE_TABLE);
        db.execSQL(ContractClass.DELETE_CHAT_TABLE);
        db.execSQL(ContractClass.DELETE_USERS_TABLE);
        onCreate(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ContractClass.DELETE_CHAT_TABLE);
        db.execSQL(ContractClass.DELETE_MESSAGE_TABLE);
        db.execSQL(ContractClass.DELETE_USERS_TABLE);
        onCreate(db);
    }
    class data
    {
        int nMessages;
        int nChats;
    }
    public data getUnreadCount(String userID)
    {
        data d = new data();
        SQLiteDatabase db = getReadableDatabase();
        Cursor result = db.rawQuery(ContractClass.COUNTUNREAD+userID,null);
        result.moveToFirst();
        while(!result.isAfterLast())
        {
            d.nMessages = result.getInt(0);
            d.nChats = result.getInt(1);
            result.moveToNext();
        }
        result.close();
        return d;
    }
    public ArrayList<Message> getMessages(int ChatID) {
        ArrayList<Message> Messages = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor result = db.rawQuery(ContractClass.GETCHATMESSAGES + ChatID, null);
        result.moveToFirst();
        while (!result.isAfterLast()) {
            int messageID = result.getInt(result.getColumnIndex(ContractClass.Message._ID));
            int chatID = result.getInt(result.getColumnIndex(ContractClass.Message._CHATID));
            int senderID = result.getInt(result.getColumnIndex(ContractClass.Message._SENDER));
            byte isread = (byte) result.getShort(result.getColumnIndex(ContractClass.Message._ISREAD));
            long timestamp = result.getLong(result.getColumnIndex(ContractClass.Message._TIMESTAMP));
            String message = result.getString(result.getColumnIndex(ContractClass.Message._MESSAGE));
            Messages.add(new Message(chatID, senderID, messageID, isread, timestamp, message));
            result.moveToNext();
        }
        result.close();
        return Messages;
    }

    public ArrayList<Chat> getAllChats() {
        ArrayList<Chat> chats = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor result = db.rawQuery(ContractClass.GETALLCHATS, null);
        result.moveToFirst();
        while (!result.isAfterLast()) {
            int ChatID = result.getInt(result.getColumnIndex(ContractClass.Chat._CHATID));
            int u1 = result.getInt(result.getColumnIndex(ContractClass.Chat._USER1));
            int u2 = result.getInt(result.getColumnIndex(ContractClass.Chat._USER2));
            chats.add(new Chat(ChatID, u1, u2));
            result.moveToNext();
        }
        result.close();
        return chats;
    }

    public boolean insertMessage(int MessageID, int ChatID, int Sender, short isread, long timestamp, String Message) {
        ContentValues cv = new ContentValues();
        cv.put(ContractClass.Message._ID, MessageID);
        cv.put(ContractClass.Message._CHATID, ChatID);
        cv.put(ContractClass.Message._SENDER, Sender);
        cv.put(ContractClass.Message._ISREAD, isread);
        cv.put(ContractClass.Message._TIMESTAMP, timestamp);
        cv.put(ContractClass.Message._MESSAGE, Message);
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.insertOrThrow(ContractClass.Message.TABLE_NAME, null, cv);

        } catch (SQLiteConstraintException e) {
            //do nothing
        }
        return true;
    }

    public boolean insertMessage(Message m) {
        return insertMessage(m.MessageID, m.ChatID, m.SenderID, m.isRead, m.timestamp, m.Message);
    }

    public boolean insertChat(int id, int user1, int user2) {
        ContentValues cv = new ContentValues();
        cv.put(ContractClass.Chat._CHATID, id);
        cv.put(ContractClass.Chat._USER1, user1);
        cv.put(ContractClass.Chat._USER2, user2);
        SQLiteDatabase db = getWritableDatabase();
        try {
            long rowID = db.insertOrThrow(ContractClass.Chat.TABLE_NAME, null, cv);
            if (rowID != -1) {
                SharedPreferences sp = HomeActivity.getInstance().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                String temp = "";
                temp = sp.getString("userID", temp);
                long userID = Long.parseLong(temp);
                    getUserInfo(user1);
                    getUserInfo(user2);
            }
        } catch (SQLiteConstraintException e) {
            //do nothing
        }
        return true;
    }

    public boolean insertChat(Chat c) {
        return insertChat(c.ChatID, c.user1, c.user2);
    }

    private void getUserInfo(int ID) {
        String url = "https://eternalvibes.me/getuserinfo/" + ID;
        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Profile p = new Profile(response.getJSONObject(0));
                    ContentValues cv = new ContentValues();
                    cv.put(ContractClass.Users._ID, p.getId());
                    cv.put(ContractClass.Users._ALIAS, p.getAlias());
                    SQLiteDatabase db = getWritableDatabase();
                    try {
                        db.insertOrThrow(ContractClass.Users.TABLE_NAME, null, cv);
                        MessengerUtil.dataUpdate();
                    } catch (SQLiteConstraintException e) {
                        //do nothing
                    }
                } catch (JSONException e) {/*DO nothing*/}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //do nothing
            }
        });
        RequestQueueSingleton.getInstance(HomeActivity.getInstance()).addToQ(jar);
    }
    public Chat hasChat(int userID)
    {
        Chat c = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor result = db.rawQuery(ContractClass.CHAT_U1 + userID, null);
        if(result.getCount() > 0)
        {
            result.moveToFirst();
            while (!result.isAfterLast()) {
                int ChatID = result.getInt(result.getColumnIndex(ContractClass.Chat._CHATID));
                int u1 = result.getInt(result.getColumnIndex(ContractClass.Chat._USER1));
                int u2 = result.getInt(result.getColumnIndex(ContractClass.Chat._USER2));
                c =new Chat(ChatID, u1, u2);
                result.moveToNext();
            }
            result.close();
        }
         result = db.rawQuery(ContractClass.CHAT_U2 + userID, null);
        if(result.getCount() > 0)
        {
            result.moveToFirst();
            while (!result.isAfterLast()) {
                int ChatID = result.getInt(result.getColumnIndex(ContractClass.Chat._CHATID));
                int u1 = result.getInt(result.getColumnIndex(ContractClass.Chat._USER1));
                int u2 = result.getInt(result.getColumnIndex(ContractClass.Chat._USER2));
                c =new Chat(ChatID, u1, u2);
                result.moveToNext();
            }
            result.close();
        }
        return c;
    }
    public String getAlias(int ID) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor result = db.rawQuery(ContractClass.GETALIAS + ID, null);
        if (result.getCount() > 0) {
            result.moveToFirst();
            return result.getString(result.getColumnIndex(ContractClass.Users._ALIAS));
        } else return null;
    }
}

