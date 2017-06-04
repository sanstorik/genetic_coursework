package develop.sanstorik.com.genetic_coursework.listViewActivity;

import android.content.DialogInterface;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Deque;

import develop.sanstorik.com.genetic_coursework.Genetic.GeneticResponse;
import develop.sanstorik.com.genetic_coursework.Genetic.Individual;
import develop.sanstorik.com.genetic_coursework.Genetic.Population;
import develop.sanstorik.com.genetic_coursework.R;


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
}
