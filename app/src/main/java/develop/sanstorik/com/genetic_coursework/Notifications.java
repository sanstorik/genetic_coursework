package develop.sanstorik.com.genetic_coursework;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;

public class Notifications {
    private Context context;
    private Service service;

    public Notifications(Context context, Service service){
        this.context = context;
        this.service = service;
    }

    void createNotification(String title, String message){
        Notification.Builder builder = getBuilder(title, message);
        context.registerReceiver(closeServiceBroadcast(), new IntentFilter("close"));

        PendingIntent intent = PendingIntent.getBroadcast(context, 0, new Intent("close"), PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(android.R.drawable.ic_menu_close_clear_cancel, "Close", intent);

        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(228, builder.build());
    }

    public void createMusicNotification(String title, String message, MediaPlayer player){
        Notification.Builder builder = getBuilder(title, message);
        context.registerReceiver(pauseMusicBroadcast(player), new IntentFilter("pause"));
        context.registerReceiver(unpauseMusicBroadcast(player), new IntentFilter("play"));

        PendingIntent play = PendingIntent.getBroadcast(context, 0, new Intent("play"), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pause = PendingIntent.getBroadcast(context, 0, new Intent("pause"), PendingIntent.FLAG_UPDATE_CURRENT);

        builder.addAction(android.R.drawable.ic_media_play, "Play", play);
        builder.addAction(android.R.drawable.ic_media_pause, "Pause", pause);

        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(228, builder.build());
    }

    private Notification.Builder getBuilder(String title, String message){
        return  new Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message);
    }

    private BroadcastReceiver pauseMusicBroadcast(MediaPlayer player){
        return new BroadcastReceiver() {
            @Override public void onReceive(Context context, Intent intent) {
                player.pause();
            }
        };
    }
    private BroadcastReceiver unpauseMusicBroadcast(MediaPlayer player){
        return new BroadcastReceiver() {
            @Override public void onReceive(Context context, Intent intent) {
                player.start();
            }
        };
    }

    private BroadcastReceiver closeServiceBroadcast(){
        return new BroadcastReceiver() {
            @Override public void onReceive(Context context, Intent intent) {
                NotificationService.doThings = false;
                context.stopService(new Intent(context, NotificationService.class));
            }
        };
    }
}
