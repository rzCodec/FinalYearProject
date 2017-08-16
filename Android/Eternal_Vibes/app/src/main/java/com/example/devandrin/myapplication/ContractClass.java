package com.example.devandrin.myapplication;

import android.provider.BaseColumns;

/**
 * Created by Devandrin on 2017/06/28.
 */

public final class ContractClass {
    public static final String CREATE_CHAT_TABLE = "CREATE TABLE " + Chat.TABLE_NAME + " (" +
            Chat._CHATID + " INTEGER PRIMARY KEY UNIQUE NOT NULL, " +
            Chat._USER1 + " INTEGER NOT NULL, " +
            Chat._USER2 + " INTEGER NOT NULL)";
    public static final String DELETE_CHAT_TABLE = "DROP TABLE IF EXISTS " + Chat.TABLE_NAME;
    public static final String CREATE_USERS_TABLE = "CREATE TABLE " + Users.TABLE_NAME + " ( " +
            Users._ID + " INTEGER PRIMARY KEY UNIQUE NOT NULL, " +
            Users._ALIAS + " TEXT NOT NULL" +
            " )";
    public static final String DELETE_USERS_TABLE = "DROP TABLE IF EXISTS " + Users.TABLE_NAME;
    public static final String CREATE_MESSAGE_TABLE = "CREATE TABLE " + Message.TABLE_NAME + " (" +
            Message._ID + " INTEGER PRIMARY KEY UNIQUE NOT NULL, " +
            Message._CHATID + " INTEGER NOT NULL, " +
            Message._SENDER + " INTEGER NOT NULL, " +
            Message._TIMESTAMP + " LONG NOT NULL, " +
            Message._ISREAD + " SHORT NOT NULL, " +
            Message._MESSAGE + " TEXT NOT NULL, " +
            " FOREIGN KEY ( " + Message._CHATID + " ) references "
            + Chat.TABLE_NAME + "(" + Chat._CHATID + ")" +
            " )";
    public static final String DELETE_MESSAGE_TABLE = "DROP TABLE IF EXISTS " + Message.TABLE_NAME;
    public static final String GETALLCHATS = "Select * from " + Chat.TABLE_NAME;
    public static final String GETCHATMESSAGES = "select * from " + Chat.TABLE_NAME +
            " inner join " + Message.TABLE_NAME +
            " on " + Chat.TABLE_NAME + "." + Chat._CHATID +
            " = " + Message.TABLE_NAME + "." + Message._CHATID +
            " where " + Chat.TABLE_NAME + "." + Chat._CHATID + " = ";
    public static final String GETALIAS = "Select * from " + Users.TABLE_NAME + " where " + Users._ID + " =";
    public static final String CHAT_U1 = GETALLCHATS + " where "+Chat._USER1+" = ";
    public static final String CHAT_U2 = GETALLCHATS + " where "+Chat._USER2+" = ";
    public static final String  COUNTUNREAD = "Select count" +
            "(" +Message._ISREAD + ")  as nMessages ,count " +
            "( DISTINCT " +Message._CHATID + ") as nChats FROM " +
            Message.TABLE_NAME +
            " where " +
            Message._ISREAD +
            " = 0 AND NOT "+Message._SENDER +" = ";
    private ContractClass() {
    }

    public static class Chat implements BaseColumns {
        public static final String TABLE_NAME = "Chat";
        public static final String _CHATID = "chatID";
        public static final String _USER1 = "user1";
        public static final String _USER2 = "user2";
    }

    public static class Users implements BaseColumns {
        public static final String TABLE_NAME = "UserCredentials";
        public static final String _ALIAS = "alias";

    }

    public static class Message implements BaseColumns {
        public static final String TABLE_NAME = "Message";
        public static final String _CHATID = "chatID";
        public static final String _SENDER = "senderID";
        public static final String _TIMESTAMP = "timestamp";
        public static final String _MESSAGE = "message";
        public static final String _ISREAD = "isread";
    }
}
