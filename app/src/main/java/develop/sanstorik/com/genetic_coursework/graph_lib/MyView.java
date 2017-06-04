package develop.sanstorik.com.genetic_coursework.graph_lib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MyView extends View {

    private int position = 50;

    public MyView(Context context){
        super(context);
    }

    public MyView(Context context, AttributeSet set){
        super(context, set);
    }

    public MyView(Context context, AttributeSet set, int val){
        super(context, set, val);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.CYAN);
        paint.setStrokeWidth(5);
        paint.setTextSize(position);
        canvas.drawCircle(position, position, 50, paint);

        canvas.drawText("HELLO", position * 2, position * 2, paint);

        Log.i("tag", "draw");

        position += 50;
    }
}
