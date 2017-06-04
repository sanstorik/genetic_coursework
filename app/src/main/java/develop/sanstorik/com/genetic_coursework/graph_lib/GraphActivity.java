package develop.sanstorik.com.genetic_coursework.graph_lib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import develop.sanstorik.com.genetic_coursework.R;

public class GraphActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        new MyView(this).invalidate();
    }


}
