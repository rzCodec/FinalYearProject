package com.example.devandrin.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Devandrin on 2017/06/28.
 */

public class DBHelper extends SQLiteOpenHelper {
    class Chat
    {
        int ChatID, user1, user2;
        Chat(int chatID, int user1, int user2) {
            ChatID = chatID;
            this.user1 = user1;
            this.user2 = user2;
        }

        @Override
        public String toString() {
            return "Chat ID: "+ChatID+
                    "\nUser 1 ID: "+user1+
                    "\nUser 2 ID: "+user2;
        }
    }
    class Message
    {
        int ChatID,SenderID;
        byte isRead;
        long timestamp;
        String Message;

        public Message(int chatID, int senderID, byte isRead, long timestamp, String Message) {
            ChatID = chatID;
            SenderID = senderID;
            this.isRead = isRead;
            this.timestamp = timestamp;
            this.Message = Message;
        }

        @Override
        public String toString() {
            return "Chat ID: "+ChatID+
                    "\nSender ID: "+SenderID+
                    "\nIs Read: "+isRead+
                    "\nTimestamp: "+timestamp+
                    "\nMessage: "+ Message;
        }
    }
    final static String DB_NAME = "Testing.db";
    final static int V = 3;
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContractClass.CREATE_CHAT_TABLE);

        db.execSQL(ContractClass.CREATE_MESSAGE_TABLE);
    }
    public DBHelper(Context c)
    {
        super(c,DB_NAME,null,V);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ContractClass.DELETE_CHAT_TABLE);
        db.execSQL(ContractClass.DELETE_MESSAGE_TABLE);
        onCreate(db);
    }
    public ArrayList<Message> getMessages(int ChatID)
    {
        ArrayList<Message> Messages = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor result = db.rawQuery(ContractClass.GETCHATMESSAGES+ChatID,null);
        result.moveToFirst();
        while(!result.isAfterLast())
        {
            int chatID = result.getInt(result.getColumnIndex(ContractClass.Message._CHATID));
            int senderID = result.getInt(result.getColumnIndex(ContractClass.Message._SENDER));
            byte isread =(byte) result.getShort(result.getColumnIndex(ContractClass.Message._ISREAD));
            long timestamp = result.getLong(result.getColumnIndex(ContractClass.Message._TIMESTAMP));
            String message = result.getString(result.getColumnIndex(ContractClass.Message._MESSAGE));
            Messages.add(new Message(chatID,senderID,isread,timestamp,message));
            result.moveToNext();
        }
        result.close();
        return Messages;
    }
    public ArrayList<Chat> getAllChats()
    {
        ArrayList<Chat> chats = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor result = db.rawQuery(ContractClass.GETALLCHATS,null);
        result.moveToFirst();
        while(!result.isAfterLast())
        {
            int ChatID = result.getInt(result.getColumnIndex(ContractClass.Chat._CHATID));
            int u1 = result.getInt(result.getColumnIndex(ContractClass.Chat._USER1));
            int u2 = result.getInt(result.getColumnIndex(ContractClass.Chat._USER2));
            chats.add(new Chat(ChatID,u1,u2));
            result.moveToNext();
        }
        result.close();
        return chats;
    }
    public boolean insertMessage(int ChatID, int Sender, short isread, long timestamp, String Message)
    {
        ContentValues cv = new ContentValues();
        cv.put(ContractClass.Message._CHATID,ChatID);
        cv.put(ContractClass.Message._SENDER,Sender);
        cv.put(ContractClass.Message._ISREAD,isread);
        cv.put(ContractClass.Message._TIMESTAMP,timestamp);
        cv.put(ContractClass.Message._MESSAGE,Message);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(ContractClass.Message.TABLE_NAME,null,cv);
        return true;
    }
    public boolean insertMessage(Message m)
    {
        return insertMessage(m.ChatID,m.SenderID,m.isRead,m.timestamp,m.Message);
    }
    public boolean insertChat(int id, int user1, int user2)
    {
        ContentValues cv = new ContentValues();
        cv.put(ContractClass.Chat._CHATID,id);
        cv.put(ContractClass.Chat._USER1,user1);
        cv.put(ContractClass.Chat._USER2,user2);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(ContractClass.Chat.TABLE_NAME,null,cv);
        return true;
    }
    public boolean insertChat(Chat c)
    {
        return insertChat(c.ChatID,c.user1,c.user2);
    }
}

