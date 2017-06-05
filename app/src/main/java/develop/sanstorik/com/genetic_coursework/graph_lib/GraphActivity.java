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
import develop.sanstorik.com.genetic_coursework.Genetic.Individual;
import develop.sanstorik.com.genetic_coursework.R;

public class GraphActivity extends AppCompatActivity {

    private GraphView graphView;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        graphView = (GraphView)findViewById(R.id.graph);

        List<Individual> individualList = new ArrayList<>();
        individualList.add(GeneticAlgorithm.spawnRandomIndividual());
        individualList.add(GeneticAlgorithm.spawnRandomIndividual());

        graphView.setIndividuals(individualList);
    }
}
