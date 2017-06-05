package develop.sanstorik.com.genetic_coursework.graph_lib;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import develop.sanstorik.com.genetic_coursework.Genetic.GeneticAlgorithm;
import develop.sanstorik.com.genetic_coursework.Genetic.GeneticResponse;
import develop.sanstorik.com.genetic_coursework.Genetic.Individual;
import develop.sanstorik.com.genetic_coursework.Genetic.Population;
import develop.sanstorik.com.genetic_coursework.R;

public class GraphActivity extends AppCompatActivity {

    private GraphView graphView;
    private List<Individual> individualList;

    private Population population;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        getIntentData();

        graphView = (GraphView)findViewById(R.id.graph);
        graphView.setIndividuals(population.getIndividuals());
    }

    private void getIntentData(){
        if(getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();
            population = bundle.getParcelable("population");
        }
    }
}
