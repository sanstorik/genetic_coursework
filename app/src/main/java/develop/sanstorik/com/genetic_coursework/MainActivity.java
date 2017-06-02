package develop.sanstorik.com.genetic_coursework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import develop.sanstorik.com.genetic_coursework.Genetic.GeneticAlgorithm;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GeneticAlgorithm.newInstance().test();
    }
}
