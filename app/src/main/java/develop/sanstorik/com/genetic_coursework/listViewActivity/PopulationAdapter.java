package develop.sanstorik.com.genetic_coursework.listViewActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import develop.sanstorik.com.genetic_coursework.R;
import develop.sanstorik.com.genetic_library.genetic.Individual;
import develop.sanstorik.com.genetic_library.genetic.Population;

class PopulationAdapter extends ArrayAdapter<Population> {

    PopulationAdapter(Context context, List<Population> data){
        super(context, 0, data);
    }

    @NonNull @Override public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Population item = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.population_list_item, parent, false);

        TextView id = (TextView)convertView.findViewById(R.id.id_tv);
        TextView sizeVal = (TextView)convertView.findViewById(R.id.size_tv);
        TextView yVal = (TextView)convertView.findViewById(R.id.yValue_tv);

        id.setText(String.valueOf(position + 1));

        if(item != null) {
            sizeVal.setText(String.valueOf(item.size()));
            Individual best = item.getBestIndividual();

            yVal.setText(String.format(Locale.getDefault(), "%.7f", best.getFunctionValue()));
        }

        return convertView;
    }
}
