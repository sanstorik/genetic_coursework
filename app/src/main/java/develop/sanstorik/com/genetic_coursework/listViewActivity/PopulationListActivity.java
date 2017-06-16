package develop.sanstorik.com.genetic_coursework.listViewActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import develop.sanstorik.com.genetic_coursework.Genetic.Population;
import develop.sanstorik.com.genetic_coursework.R;
import develop.sanstorik.com.genetic_coursework.graph_lib.GraphActivity;

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

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.toolbar_menu, menu);

        ActionBar bar = getSupportActionBar();
        if(bar != null)
            bar.setTitle("Generation, #" + populationIndex);

        return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.graphItem:
                startGraphActivity();
                break;
            case R.id.backItem:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
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

    private void startGraphActivity(){
        Intent intent = new Intent(this, GraphActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("population", population);

        intent.putExtras(bundle);
        startActivity(intent);
    }
}
