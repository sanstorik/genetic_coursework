package develop.sanstorik.com.genetic_coursework;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class PreferenceActivity extends Activity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        getFragmentManager().beginTransaction()
                .add(R.id.activity_preference, new SettingsFragment())
                .commit();

        findViewById(R.id.goAnother).setOnClickListener(
                (event) -> Toast.makeText(this,
                        PreferenceManager.getDefaultSharedPreferences(this).getString("pref_sync_list", "null"), Toast.LENGTH_SHORT).show() );

    }
}
