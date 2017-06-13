package develop.sanstorik.com.genetic_coursework;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class Notifications {
    private Context context;
    private Service service;

    public Notifications(Context context){
        this.context = context;
    }
    public Notifications(Context context, Service service){
        this.context = context;
        this.service = service;
    }

    public void createNotification(String title, String message){
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.cat1)
                .setContentTitle(title)
                .setContentText(message);

        //builder.addAction(R.drawable.arrow_axis_x, "Close"

        PendingIntent intent = PendingIntent.getBroadcast(context, 0, new Intent("myFilter"), PendingIntent.FLAG_UPDATE_CURRENT);
        context.registerReceiver(new BroadcastReceiver() {
            @Override public void onReceive(Context context, Intent intent) {
                service.stopSelf();
            }
        }, new IntentFilter("myFilter"));

        Notification notification = builder.build();

        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(228, notification);
    }
}
