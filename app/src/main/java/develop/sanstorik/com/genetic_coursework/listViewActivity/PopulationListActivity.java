package develop.sanstorik.com.genetic_coursework.listViewActivity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ListView;

import develop.sanstorik.com.genetic_coursework.Genetic.Population;
import develop.sanstorik.com.genetic_coursework.R;

public class PopulationListActivity extends AppCompatActivity {
    private ListView generations;
    private int populationIndex;
    private Population population;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_population_list);

        generations = (ListView)findViewById(R.id.dataList);
        getInputGenerationData();
    }

    private void getInputGenerationData(){
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            population = bundle.getParcelable("population");
            populationIndex = bundle.getInt("index");

            if (population != null)
                generations.setAdapter(new IndividualAdapter(this, population.getIndividuals()));
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar bar = getSupportActionBar();

        if(bar != null)
            bar.setTitle("Generation, #" + populationIndex);

        return super.onCreateOptionsMenu(menu);
    }
}
