package develop.sanstorik.com.genetic_coursework;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class PreferenceActivity extends Activity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Configuration config = new Configuration(getResources().getConfiguration());
            config.setLocale(new Locale("ru", "RU"));

            Log.i("tag", "123");
        }

        getFragmentManager().beginTransaction()
                .add(R.id.activity_preference, new SettingsFragment())
                .commit();

        findViewById(R.id.goAnother).setOnClickListener(
                (event) -> Toast.makeText(this,
                        PreferenceManager.getDefaultSharedPreferences(this).getString("pref_sync_list", "null"), Toast.LENGTH_SHORT).show() );

    }
}
