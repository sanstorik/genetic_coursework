package develop.sanstorik.com.genetic_coursework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import develop.sanstorik.com.genetic_coursework.Genetic.GeneticAlgorithm;
import develop.sanstorik.com.genetic_coursework.Genetic.InterruptionSource;
import develop.sanstorik.com.genetic_coursework.database.AuthorizeUserDatabase;
import develop.sanstorik.com.genetic_coursework.database.IndividualDatabase;
import develop.sanstorik.com.genetic_coursework.listViewActivity.ListGeneticActivity;

public class MainActivity extends AppCompatActivity {
    private InterruptionSource.Interruptible interruptionSource;
    private Spinner interruptSpinner;
    private TextView interruptValue;
    private int currentSpinnerPos;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        interruptValue = (TextView)findViewById(R.id.intSource_value);
        createInterruptionSourceSpinner();

        findViewById(R.id.algo_button).setOnClickListener((event) -> startAlgorithm());
        interruptionSource = InterruptionSource.createIterationSource(
                Integer.valueOf(interruptValue.getText().toString()));

        registerForContextMenu(interruptValue);

        Log.i("tag", String.valueOf(getPreferences(MODE_PRIVATE).getString("pref_sync_list", "null")));
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.toolbar_register, menu);

        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.loginTlb:
                showUserDataDialog(false);
                break;
            case R.id.registerTlb:
                showUserDataDialog(true);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //register = true -- log in = false
    private void showUserDataDialog(boolean registerUser){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(R.layout.userdata_dialog)
                .show();

        EditText userName = (EditText)alertDialog.findViewById(R.id.userName_et);
        EditText password = (EditText)alertDialog.findViewById(R.id.password_et);
        if(userName == null || password == null)
            return;

        alertDialog.findViewById(R.id.userData_btn).setOnClickListener(
                (event)->{
                    if(registerUser)
                        AuthorizeUserDatabase.connection(MainActivity.this, IndividualDatabase.SQLmode.WRITE)
                        .registerUser(userName.getText().toString(), password.getText().toString());
                    else
                        Toast.makeText(MainActivity.this,
                                String.valueOf(AuthorizeUserDatabase.connection(MainActivity.this, IndividualDatabase.SQLmode.READ)
                                .userIsValid(userName.getText().toString(), password.getText().toString()))
                                , Toast.LENGTH_SHORT).show();

                    alertDialog.dismiss();
                }
        );
    }

    private void startAlgorithm(){
        EditText mutability = (EditText) findViewById(R.id.mutability_et);
        EditText breeding = (EditText) findViewById(R.id.breeding_et);

        if (mutability.getText().toString().isEmpty()
                || breeding.getText().toString().isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Wrong input")
                    .setIcon(R.drawable.arrow_axis_x)
                    .setMessage("Mutability should be in range (0;1)" +
                            "\nbreeding should be in range (0;1000)")
                    .setNegativeButton("OK", (dialog, id)-> dialog.cancel())
                    .show();
            return;
        }

        float mutabilityChance = Float.valueOf(mutability.getText().toString());
        int breedingCount = Integer.valueOf(breeding.getText().toString());

        if(dataIsCorrect(mutabilityChance, breedingCount)) {
            GeneticAlgorithm.newInstance(breedingCount, mutabilityChance, interruptionSource).solve();
            startListActivity();
        }
        else
            Toast.makeText(this, "Wrong input", Toast.LENGTH_SHORT).show();
    }

    private boolean dataIsCorrect(double mutability, int breeding){
        return (mutability > 0 && mutability < 1)
                && (breeding > 0 && breeding < 1000);
    }

    private void startListActivity(){
        Intent intent = new Intent(this, ListGeneticActivity.class);
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
        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialog)
                .setView(R.layout.intsource_dialog)
                .setOnCancelListener((dialog)-> {
                    interruptSpinner.setSelection(previousPos);
                    currentSpinnerPos = previousPos;
                }).show();

        String[] hints = new String[]{"Iterations", "Function value", "Function value"};

        EditText editValue = (EditText)alertDialog.findViewById(R.id.sourceValue);
        Button input = (Button)alertDialog.findViewById(R.id.source_ok_button);

        if(editValue == null || input == null) {
            alertDialog.dismiss();
            return;
        }

        if(sourcePosition == 0)
            editValue.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        else
            editValue.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        editValue.setHint(hints[sourcePosition]);

        input.setOnClickListener((event) -> {
            if(editValue.getText().toString().isEmpty()) {
                alertDialog.cancel();
                return;
            }
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
