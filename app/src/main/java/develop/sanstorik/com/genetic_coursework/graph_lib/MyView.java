package develop.sanstorik.com.genetic_coursework.graph_lib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class MyView extends View {
    public MyView(Context context){
        super(context);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(50);
        paint.setTextSize(50);
        canvas.drawCircle(50,50, 150, paint);

        canvas.drawText("HELLO", 100, 100, paint);

        Log.i("tag", "draw");
    }
}
