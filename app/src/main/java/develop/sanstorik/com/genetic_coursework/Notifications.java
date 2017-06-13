package develop.sanstorik.com.genetic_coursework;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

class Notifications {
    private Context context;

    Notifications(Context context){
        this.context = context;
    }

    void createNotification(){
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.cat1)
                .setContentTitle("Cat")
                .setContentText("Cat is running.");


        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(228, builder.build());
    }
}
