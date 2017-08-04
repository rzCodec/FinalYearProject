
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class


Notifications extends BroadcastReceiver
{
	private String msg; //Short Description of what the notification is
	private String msgText; //Extended description of the notification
	private String alert; //

	public Notifications(String msg, String msgText, String alert)
	{
		this.msg = msg;
		this.msgText = msgText;
		this.alert = alert;
	}
	
    @Override
    public void onReceive(Context context, Intent intent) 
	{
        createNotification(context,this.msg, this.msgText,this.alert);

    }

    public void createNotification(Context context, String msg, String msgText, String alert)
    {
        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0,
                new Intent(context),0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(msg)
                .setTicker(alert)
                .setContentText(msgText);


        mBuilder.setContentIntent(notificationIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());

    }
	
	 
}