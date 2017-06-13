package develop.sanstorik.com.genetic_coursework;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class NotificationService extends IntentService {

    public static boolean doThings = true;
    public NotificationService(){
        super("thread");
    }

    @Override protected void onHandleIntent(Intent intent) {
        while(doThings){
            new Notifications(getApplicationContext(), this).createNotification("cat", "cat is running");

            try{
                Thread.sleep(1000);
            }
            catch (InterruptedException e){
                Log.e("tag", e.toString());
            }
        }

        doThings = true;
    }
}
