package develop.sanstorik.com.genetic_coursework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

import develop.sanstorik.com.genetic_coursework.Genetic.GeneticAlgorithm;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createSpinner();
       // GeneticAlgorithm.newInstance(10, 0.25f).solve();
    }

    private void createSpinner(){
        String[] list = {"First main errorrrrr", "2", "3", "4"};
        Spinner spinner = (Spinner)findViewById(R.id.intSource_spinner);
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_row, list);

        spinner.setAdapter(spinnerAdapter);
    }
}
