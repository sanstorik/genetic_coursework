package develop.sanstorik.com.genetic_coursework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Deque;

import develop.sanstorik.com.genetic_coursework.Genetic.GeneticResponse;
import develop.sanstorik.com.genetic_coursework.Genetic.Individual;
import develop.sanstorik.com.genetic_coursework.Genetic.Population;

public class ListGeneticActivity extends AppCompatActivity {

    private Deque<Individual> bestIndividualsInGen;
    private Deque<Population> generations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_genetic);

        bestIndividualsInGen = GeneticResponse.getInstance().getBestIndividalsInEachGeneration();
        generations = GeneticResponse.getInstance().getGenerations();

        Log.i("tag", "data is set");
    }
}
