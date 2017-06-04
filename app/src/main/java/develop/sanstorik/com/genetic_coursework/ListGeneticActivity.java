package develop.sanstorik.com.genetic_coursework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import develop.sanstorik.com.genetic_coursework.Genetic.Individual;
import develop.sanstorik.com.genetic_coursework.Genetic.Population;

public class ListGeneticActivity extends AppCompatActivity {

    private ArrayList<Individual> bestIndividualsInGen;
    private ArrayList<Population> generations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_genetic);

        if(getIntent().getParcelableArrayListExtra("bestInd") != null) {
            ArrayList<Individual> individuals = getIntent().getParcelableArrayListExtra("bestInd");
            ArrayList<Population> generations = getIntent().getParcelableArrayListExtra("generations");

            Log.i("tag", "data is set" );
        }
    }
}
