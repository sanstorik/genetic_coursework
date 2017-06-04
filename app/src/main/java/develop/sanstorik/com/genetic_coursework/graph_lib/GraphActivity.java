package develop.sanstorik.com.genetic_coursework.graph_lib;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import develop.sanstorik.com.genetic_coursework.R;

public class GraphActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        ImageView imageView = (ImageView)findViewById(R.id.imageView3);
        Bitmap temp = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(temp);

        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(50);
        canvas.drawText("zalupa mamonta", 200, 200, paint);

        imageView.setImageBitmap(temp);

        MyView myView = (MyView)findViewById(R.id.myView);

        findViewById(R.id.button).setOnClickListener((event)-> myView.invalidate());
    }


}
