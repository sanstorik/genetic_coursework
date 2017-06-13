package develop.sanstorik.com.genetic_coursework;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class NotificationService extends IntentService {

    public NotificationService(){
        super("thread");
    }

    @Override protected void onHandleIntent(Intent intent) {
        while(true){
            new Notifications(this).createNotification();

            try{
                Thread.sleep(1000);
            }
            catch (InterruptedException e){
                Log.e("tag", e.toString());
            }
        }
    }
}
