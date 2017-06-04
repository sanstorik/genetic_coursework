package develop.sanstorik.com.genetic_coursework.listViewActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import develop.sanstorik.com.genetic_coursework.Genetic.Individual;
import develop.sanstorik.com.genetic_coursework.R;

class IndividualAdapter extends ArrayAdapter<Individual> {

    IndividualAdapter(Context context, List<Individual> data){
        super(context, 0, data);
    }

    @NonNull @Override public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Individual individual = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.individual_list_item, parent, false);

        TextView id = (TextView)convertView.findViewById(R.id.id_tv);
        TextView bits = (TextView)convertView.findViewById(R.id.genesBits);
        TextView xVal = (TextView)convertView.findViewById(R.id.xValue_tv);
        TextView yVal = (TextView)convertView.findViewById(R.id.yValue_tv);

        id.setText(String.valueOf(position + 1));
        if(individual != null) {
            bits.setText(individual.getBits());
            xVal.setText(String.format(Locale.getDefault(), "x=%.3f", individual.getGenesValue()));
            yVal.setText(String.format(Locale.getDefault(), "y=%.5f", individual.getFunctionValue()));
        }

        return convertView;
    }
}
