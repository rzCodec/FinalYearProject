package com.example.devandrin.myapplication;

/**
 * Created by Devandrin on 2017/08/16.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.*;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.os.Message;

/**
 * Created by Devandrin on 2017/08/13.
 */

public class EVService extends Service{
    private static final String TAG ="Messenger Service";
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private String userID = null;
    private boolean running = true;
    private final MessengerBinder binder = new MessengerBinder();
    public class MessengerBinder extends Binder
    {
        public EVService getService()
        {
            return EVService.this;
        }
    }
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            //// TODO: 2017/08/16 Handle Work to do
            if(HomeActivity.getDbHelper() == null)
            {
                HomeActivity.setDbHelper(new DBHelper(getApplicationContext()));
            }
            RequestQueueSingleton.getInstance(getApplicationContext());
            int NumbMessages = -1;
            while(running)
            {
                try
                {
                    MessengerUtil.makeRequest(userID);
                    DBHelper.data d = HomeActivity.getDbHelper().getUnreadCount(userID);
                    if(NumbMessages != d.nMessages && d.nMessages > 0 && d.nChats>0)
                    {
                        Notify("Eternal Vibes",d.nMessages+" new messages from "+d.nChats+" chats");
                        NumbMessages = d.nMessages;
                    }

                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }

                try
                {
                    Thread.sleep(10000L);
                }catch(InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Thread.MAX_PRIORITY);
        thread.start();
        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        running = true;
        SharedPreferences sp = getSharedPreferences("userInfo",MODE_PRIVATE);
        userID = sp.getString("userID","");
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    public void Notify(String Title,String Message)
    {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_info_black_24dp)
                .setContentTitle(Title)
                .setDefaults(
                        Notification.DEFAULT_VIBRATE|
                        Notification.DEFAULT_SOUND|
                        Notification.DEFAULT_LIGHTS
                )
                .setAutoCancel(true)
                .setVisibility(Notification.VISIBILITY_PRIVATE)
                .setLights(Color.argb(100,238,130,238),100,100)
                .setColor(Color.MAGENTA)
                .setWhen(System.currentTimeMillis()-20000L)
                .setContentText(Message);
        Intent i = new Intent(this, Dashboard.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(HomeActivity.class);
        stackBuilder.addNextIntent(i);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager m = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        m.notify(0,notificationBuilder.build());

    }

    @Override
    public void onDestroy() {
        running = false;
        super.onDestroy();
    }
}
