package develop.sanstorik.com.genetic_coursework.music_player;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

import develop.sanstorik.com.genetic_coursework.Notifications;
import develop.sanstorik.com.genetic_coursework.R;

public class MusicPlayerService extends Service{

    public static final String ACTION_PLAY = "develop.sanstorik.com.genetic_coursework.music_player.PLAY";
    private MediaPlayer mediaPlayer;

    @Override public int onStartCommand(Intent intent, int flags, int startId){
        if(intent.getAction().equals(ACTION_PLAY)){
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource("http://www.brothershouse.narod.ru/music/pepe_link_-_guitar_vibe_113_club_mix.mp3");
            }
            catch (IOException e){
                Log.e("tag", e.toString());
            }
            mediaPlayer.setOnPreparedListener(MediaPlayer::start);
            mediaPlayer.prepareAsync();

            new Notifications(this).createNotification("Music", "Guitar song is playing");
        }

        return START_NOT_STICKY;
    }

    @Nullable @Override public IBinder onBind(Intent intent) {

        return null;
    }

    @Override public void onDestroy() {
        if(mediaPlayer != null)
            mediaPlayer.release();

        super.onDestroy();
    }
}
