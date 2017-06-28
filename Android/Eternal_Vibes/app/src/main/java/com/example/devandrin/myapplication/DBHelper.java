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
    public class Chat
    {
        public int ChatID;
        public int user1;
        public int user2;
        public Chat(int chatID, int user1, int user2) {
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
    public final static String DB_NAME = "Testing.db";
    public final static int V = 1;
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
    public ArrayList<Chat> getAllChats()
    {
        ArrayList<Chat> chats = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor result = db.rawQuery("Select * from "+ContractClass.Chat.TABLE_NAME,null);
        result.moveToFirst();
        while(!result.isAfterLast())
        {
            int ChatID = result.getInt(result.getColumnIndex(ContractClass.Chat._CHATID));
            int u1 = result.getInt(result.getColumnIndex(ContractClass.Chat._USER1));
            int u2 = result.getInt(result.getColumnIndex(ContractClass.Chat._USER2));
            chats.add(new Chat(ChatID,u1,u2));
            result.moveToNext();
        }
        return chats;
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
}

