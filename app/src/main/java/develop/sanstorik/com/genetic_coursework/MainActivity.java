package develop.sanstorik.com.genetic_coursework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import develop.sanstorik.com.genetic_coursework.Genetic.GeneticAlgorithm;
import develop.sanstorik.com.genetic_coursework.Genetic.GeneticResponse;
import develop.sanstorik.com.genetic_coursework.Genetic.InterruptionSource;

public class MainActivity extends AppCompatActivity {

    private InterruptionSource.Interruptible interruptionSource;
    private Spinner interruptSpinner;
    private TextView interruptValue;
    private int currentSpinnerPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        interruptValue = (TextView)findViewById(R.id.intSource_value);
        createInterruptionSourceSpinner();

        findViewById(R.id.algo_button).setOnClickListener((event) -> startAlgorithm());
        interruptionSource = InterruptionSource.createIterationSource(
                Integer.valueOf(interruptValue.getText().toString()));
    }

    private void startAlgorithm(){
        EditText mutability = (EditText) findViewById(R.id.mutability_et);
        EditText breeding = (EditText) findViewById(R.id.breeding_et);

        if (mutability.getText().toString().isEmpty()
                || breeding.getText().toString().isEmpty()) {
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
            return;
        }

        float mutabilityChance = Float.valueOf(mutability.getText().toString());
        int breedingCount = Integer.valueOf(breeding.getText().toString());

        if(dataIsCorrect(mutabilityChance, breedingCount)) {
            GeneticResponse response = GeneticAlgorithm.newInstance(breedingCount, mutabilityChance, interruptionSource).solve();
            fetchDataToListActivity(response);
        }
        else
            Toast.makeText(this, "Wrong input", Toast.LENGTH_SHORT).show();
    }

    private boolean dataIsCorrect(double mutability, int breeding){
        return (mutability > 0 && mutability < 1)
                && (breeding > 0 && breeding < 1000);
    }

    private void fetchDataToListActivity(GeneticResponse response){
        Intent intent = new Intent(this, ListGeneticActivity.class);
        intent.putParcelableArrayListExtra("bestInd", response.getBestIndividalsInEachGeneration());
        intent.putParcelableArrayListExtra("generations", response.getGenerations());

        startActivity(intent);
    }

    private void createInterruptionSourceSpinner(){
        String[] options = {"Iteration stop", "Func value max stop", "Func value min stop"};
        interruptSpinner = (Spinner)findViewById(R.id.intSource_spinner);
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_row, options);

        interruptSpinner.setAdapter(spinnerAdapter);
        interruptSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != currentSpinnerPos)
                    alertDialogInterruptionSource(position, currentSpinnerPos);
                currentSpinnerPos = position;
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /*
    getting all needed data
    for interruption source
    for example count of iterations to stop
     */
    private void alertDialogInterruptionSource(final int sourcePosition, final int previousPos){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(R.layout.intsource_dialog)
                .setOnCancelListener((dialog)-> {
                    interruptSpinner.setSelection(previousPos);
                    currentSpinnerPos = previousPos;
                }).show();

        String[] textViews = new String[]{"Count of iterations to stop", "Max function value to stop",
        "Min function value to stop"};
        String[] hints = new String[]{"Iterations", "Function value", "Function value"};

        TextView textView = (TextView)alertDialog.findViewById(R.id.sourceInput);
        EditText editValue = (EditText)alertDialog.findViewById(R.id.sourceValue);
        Button input = (Button)alertDialog.findViewById(R.id.source_ok_button);

        if(textView == null || editValue == null || input == null) {
            alertDialog.dismiss();
            return;
        }

        textView.setText(textViews[sourcePosition]);

        if(sourcePosition == 0)
            editValue.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        else
            editValue.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        editValue.setHint(hints[sourcePosition]);

        input.setOnClickListener((event) -> {
            inputInterruptionSourceData(sourcePosition, editValue.getText());
            alertDialog.dismiss();
        });
    }

    private void inputInterruptionSourceData(final int sourcePosition, Editable value){
        double convertedValue = Double.valueOf(value.toString());
        interruptValue.setText(String.valueOf(convertedValue));

        switch (sourcePosition){
            case 0:
                interruptionSource = InterruptionSource.createIterationSource((int)convertedValue);
                break;
            case 1:
                interruptionSource = InterruptionSource.createFunctionMaxValueSource(convertedValue);
                break;
            case 2:
                interruptionSource = InterruptionSource.createFunctionMinValueSource(convertedValue);
                break;
        }
    }
}
