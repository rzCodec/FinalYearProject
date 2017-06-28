package com.example.devandrin.myapplication;

import android.provider.BaseColumns;

/**
 * Created by Devandrin on 2017/06/28.
 */

public final class ContractClass
{
    private ContractClass(){}
    public static class Chat implements BaseColumns
    {
        public static final String TABLE_NAME = "Chat";
        public static final String _CHATID = "chatID";
        public static final String _USER1 = "user1";
        public static final String _USER2 = "user2";
    }
    public static class Message implements BaseColumns
    {
        public static final String TABLE_NAME = "Message";
        public static final String _CHATID = "chatID";
        public static final String _SENDER = "senderID";
        public static final String _TIMESTAMP = "timestamp";
        public static final String _MESSAGE = "message";
        public static final String _ISREAD = "isread";
    }
    public static final String CREATE_CHAT_TABLE ="CREATE TABLE "+Chat.TABLE_NAME+" (" +
            Chat._CHATID + " INTEGER PRIMARY KEY UNIQUE NOT NULL, "+
            Chat._USER1 + " INTEGER NOT NULL, "+
            Chat._USER2 + " INTEGER NOT NULL)";
    public static final String DELETE_CHAT_TABLE ="DROP TABLE IF EXISTS "+Chat.TABLE_NAME;
    public static final String CREATE_MESSAGE_TABLE ="CREATE TABLE "+Message.TABLE_NAME+" (" +
            Message._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+
            Message._CHATID + " INTEGER NOT NULL, "+
            Message._SENDER + " INTEGER NOT NULL, "+
            Message._TIMESTAMP + " INTEGER NOT NULL, "+
            Message._ISREAD + " INTEGER NOT NULL, "+
            Message._MESSAGE + " TEXT NOT NULL, " +
            "FOREIGN KEY ( " +Message._CHATID+" ) references "
            +Chat.TABLE_NAME+"("+Chat._CHATID+")"+
            " )";
    public static final String DELETE_MESSAGE_TABLE ="DROP TABLE IF EXISTS "+Message.TABLE_NAME;
}
