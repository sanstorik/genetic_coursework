package develop.sanstorik.com.genetic_coursework;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import develop.sanstorik.com.genetic_coursework.Genetic.GeneticAlgorithm;
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
