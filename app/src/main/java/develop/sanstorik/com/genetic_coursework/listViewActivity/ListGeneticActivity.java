package develop.sanstorik.com.genetic_coursework.listViewActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import develop.sanstorik.com.genetic_coursework.Genetic.GeneticResponse;
import develop.sanstorik.com.genetic_coursework.Genetic.Individual;
import develop.sanstorik.com.genetic_coursework.Genetic.Population;
import develop.sanstorik.com.genetic_coursework.R;
import develop.sanstorik.com.genetic_coursework.graph_lib.GraphActivity;


public class ListGeneticActivity extends AppCompatActivity {

    private enum DataType{
        BEST_INDIVIDUALS, GENERATIONS
    }

    private ArrayList<Individual> bestIndividualsInGen;
    private ArrayList<Population> generations;
    private ListView listView;
    private DataType currentDataType;

    private ArrayAdapter<Individual> bestIndividualsDataAdapter;
    private ArrayAdapter<Population> generationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_genetic);

        bestIndividualsInGen = GeneticResponse.getInstance().getBestIndividalsInEachGeneration();
        generations = GeneticResponse.getInstance().getGenerations();
        listView = (ListView)findViewById(R.id.dataList);

        fillListWithData();
        setRadioClickListeners();
        registerListViewPopulationSelected();
    }

    private void fillListWithData(){
        currentDataType = DataType.BEST_INDIVIDUALS;

        bestIndividualsDataAdapter = new IndividualAdapter(this, bestIndividualsInGen);
        listView.setAdapter(bestIndividualsDataAdapter);

        generationsAdapter = new PopulationAdapter(this, generations);
    }

    private void switchListData(DataType dataType){
        if(currentDataType == dataType)
            return;

        currentDataType = dataType;
        switch (dataType){
            case BEST_INDIVIDUALS:
                listView.setAdapter(bestIndividualsDataAdapter);
                break;
            case GENERATIONS:
                listView.setAdapter(generationsAdapter);
                break;
        }
    }

    private void setRadioClickListeners(){
        View.OnClickListener listener = (event) -> {
            switch (event.getId()){
                case R.id.radioGenerations:
                    switchListData(DataType.GENERATIONS);
                    break;
                case R.id.radioIndividuals:
                    switchListData(DataType.BEST_INDIVIDUALS);
                    break;
            }
        };

        findViewById(R.id.radioGenerations).setOnClickListener(listener);
        findViewById(R.id.radioIndividuals).setOnClickListener(listener);
    }

    private void registerListViewPopulationSelected() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
                    if (currentDataType == DataType.GENERATIONS)
                        startPopulationActivity((Population) parent.getItemAtPosition(position), position);
                }
        );
    }

    private void startPopulationActivity(Population population, int index){
        /*Intent intent = new Intent(this, PopulationListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("population", population);
        bundle.putInt("index", index + 1);*/

        Intent intent = new Intent(this, GraphActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("population", population);

        intent.putExtras(bundle);
        startActivity(intent);
    }
}
