package org.chuck.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

public class NotificationUtil {
	private Context context;
	public NotificationUtil(Context context) {
		this.context=context;
	}
	public void notify(int resId, int icon, CharSequence title, CharSequence text,
					   CharSequence tickerText, int number, PendingIntent intent){
//		RemoteViews remoteViews=new RemoteViews("", 2);		
		NotificationCompat.Builder notifyBuilder=
				new NotificationCompat.Builder(context)
				.setSmallIcon(icon)
				.setContentTitle(title)
				.setContentText(text)
				.setTicker(tickerText)
				.setNumber(number)
//				.setSound(sound, streamType)
				.setDefaults(Notification.DEFAULT_VIBRATE);
//				.setContentIntent(intent);
		
		Notification notification=notifyBuilder.build();
//		if(Build.VERSION.SDK_INT<16){
//			notification.contentView=remoteViews;
//		}
		
		NotificationManager notifyManager=
				(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notifyManager.notify("TAG", 0, notification);
	}		
	
}
