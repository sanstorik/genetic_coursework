package develop.sanstorik.com.genetic_coursework.listViewActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Locale;

import develop.sanstorik.com.genetic_coursework.R;
import develop.sanstorik.com.genetic_coursework.genetic.GeneticResponse;
import develop.sanstorik.com.genetic_coursework.genetic.Individual;
import develop.sanstorik.com.genetic_coursework.genetic.Population;
import develop.sanstorik.com.genetic_coursework.graph_lib.GraphActivity;


public class ListGeneticActivity extends AppCompatActivity {

    private enum DataType {
        BEST_INDIVIDUALS, GENERATIONS
    }

    private ArrayList<Individual> bestIndividualsInGen;
    private ArrayList<Population> generations;
    private ListView listView;
    private DataType currentDataType;

    private ArrayAdapter<Individual> bestIndividualsDataAdapter;
    private ArrayAdapter<Population> generationsAdapter;

    private RadioButton individualsRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_genetic);

        bestIndividualsInGen = GeneticResponse.getInstance().getBestIndividalsInEachGeneration();
        generations = GeneticResponse.getInstance().getGenerations();
        listView = (ListView) findViewById(R.id.dataList);

        fillListWithData();
        setRadioClickListeners();
        registerListViewPopulationSelected();

        registerForContextMenu(listView);
    }

    @Override public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        Individual ind;

        if (listView.getItemAtPosition(info.position) instanceof Individual) {
            ind = (Individual) listView.getItemAtPosition(info.position);
            menu.add(1, 1, 1, String.format(Locale.getDefault(), "y = %.10f", ind.getFunctionValue()));
        }

        menu.add(1, 2, 1, "save");
    }

    @Override public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.toolbar_save_load_menu, menu);

        ActionBar bar = getSupportActionBar();
        if (bar != null)
            bar.setTitle("");

        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.graphItem:
                startGraphActivity();
                break;
            case R.id.backItem:
                onBackPressed();
                break;
            case R.id.saveTlb:
                askForFileName("Save", true);
                break;
            case R.id.loadTlb:
                askForFileName("Load", false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //save - true; load - false
    private void askForFileName(String buttonName, boolean save){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(R.layout.intsource_dialog)
                .setTitle("Input file name")
                .show();

        EditText editText = (EditText)alertDialog.findViewById(R.id.sourceValue);
        Button button = (Button)alertDialog.findViewById(R.id.source_ok_button);


        if(editText != null && button != null) {
            editText.setHint("File name");
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            button.setText(buttonName);

            if(save)
                button.setOnClickListener(
                        (event)-> {
                            serializeIndividualsList(editText.getText().toString());
                            alertDialog.dismiss();
                        }
                );
            else
                button.setOnClickListener(
                        (event)->{
                            readSerializedIndividualsList(editText.getText().toString());
                            alertDialog.dismiss();
                        }
                );
        }
    }

    private void fillListWithData() {
        currentDataType = DataType.BEST_INDIVIDUALS;

        bestIndividualsDataAdapter = new IndividualAdapter(this, bestIndividualsInGen);
        listView.setAdapter(bestIndividualsDataAdapter);

        generationsAdapter = new PopulationAdapter(this, generations);
    }

    private void switchListData(DataType dataType) {
        if (currentDataType == dataType)
            return;

        currentDataType = dataType;
        switch (dataType) {
            case BEST_INDIVIDUALS:
                listView.setAdapter(bestIndividualsDataAdapter);
                break;
            case GENERATIONS:
                listView.setAdapter(generationsAdapter);
                break;
        }
    }

    private void setRadioClickListeners() {
        View.OnClickListener listener = (event) -> {
            switch (event.getId()) {
                case R.id.radioGenerations:
                    switchListData(DataType.GENERATIONS);
                    break;
                case R.id.radioIndividuals:
                    switchListData(DataType.BEST_INDIVIDUALS);
                    break;
            }
        };

        individualsRadioButton = (RadioButton)findViewById(R.id.radioIndividuals);
        individualsRadioButton.setOnClickListener(listener);
        findViewById(R.id.radioGenerations).setOnClickListener(listener);
    }

    private void registerListViewPopulationSelected() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
                    if (currentDataType == DataType.GENERATIONS)
                        startPopulationActivity((Population) parent.getItemAtPosition(position), position);
                }
        );
    }

    private void startPopulationActivity(Population population, int index) {
        Intent intent = new Intent(this, PopulationListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("population", population);
        bundle.putInt("index", index + 1);

        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void startGraphActivity() {
        Intent intent = new Intent(this, GraphActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("individuals", bestIndividualsInGen);

        intent.putExtras(bundle);
        startActivity(intent);
    }


    private void serializeIndividualsList(String fileName) {
        File file = new File(getBaseContext().getFilesDir(), fileName + ".txt");
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(bestIndividualsInGen);
        } catch (IOException e) {
            Log.i("tag", e.toString());
        } finally {
            try {
                if (fileOutputStream != null)
                    fileOutputStream.close();

                if(objectOutputStream != null)
                    objectOutputStream.close();
            } catch (IOException e) {/*EMPTRY*/}
        }
    }

    @SuppressWarnings("unchecked")
    private void readSerializedIndividualsList(String fileName) {
        File file = new File(getBaseContext().getFilesDir(), fileName + ".txt");
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);

            bestIndividualsInGen = (ArrayList<Individual>)objectInputStream.readObject();
            bestIndividualsDataAdapter = new IndividualAdapter(this, bestIndividualsInGen);

            listView.setAdapter(bestIndividualsDataAdapter);
            individualsRadioButton.performClick();
        } catch (IOException|ClassNotFoundException e) {
            Log.i("tag", e.toString());
            Toast.makeText(this, "Loading failed", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();

                if(objectInputStream != null)
                    objectInputStream.close();
            } catch (IOException e) {/*EMPTY*/}
        }
    }
}
