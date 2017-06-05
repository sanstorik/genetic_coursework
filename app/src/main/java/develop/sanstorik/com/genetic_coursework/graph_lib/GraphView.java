package develop.sanstorik.com.genetic_coursework.graph_lib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import develop.sanstorik.com.genetic_coursework.Genetic.Individual;
import develop.sanstorik.com.genetic_coursework.Genetic.Population;
import develop.sanstorik.com.genetic_coursework.R;

public class GraphView extends View {
    private List<Individual> individuals;
    private Point[] corners;

    private Paint pointPaint;
    private Paint axisPaint;
    private Paint textPaint;

    //x=0, y=0
    private Point nilPoint;
    private Point endX;
    private Point endY_positive;
    private Point endY_negative;

    private final int POINT_RADIUS = 10;
    Canvas canvas;

    public GraphView(Context context){
        super(context);

        init();
    }

    public GraphView(Context context, AttributeSet set){
        super(context, set);

        init();
    }

    public GraphView(Context context, AttributeSet set, int val){
        super(context, set, val);

        init();
    }

    void setIndividuals(List<Individual> individuals){
        this.individuals = individuals;
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        initSizes();
    }

    private void initSizes(){
        //left-bottom -- clockwise
        corners = new Point[4];
        corners[0] = new Point(0, getHeight());
        corners[1] = new Point(0,0);
        corners[2] = new Point(getWidth(), 0);
        corners[3] = new Point(getWidth(), getHeight());

    }

    private void init(){
        pointPaint = new Paint();
        pointPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setColor(Color.RED);
        pointPaint.setStrokeWidth(20);

        axisPaint = new Paint(pointPaint);
        axisPaint.setColor(Color.BLACK);
        axisPaint.setStrokeWidth(10);

        textPaint = new Paint(pointPaint);
        textPaint.setTextSize(50);
        textPaint.setColor(Color.BLUE);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;

        drawAxises();

        for(int i=0; i < 10; i++){
            drawPoint(new Point(virtualPoints(i), virtualPointY(i)));
        }
    }

    /*
    @param count get needed point on X axis
    @returns this point, recalculated to scale
     */
    private int virtualPoints(int count){
        return nilPoint.getX() + (nilPoint.lengthX(endX) / 20) * count;
    }

    private int virtualPointY(int count){
        if(count < 0)
            return nilPoint.getY() - (nilPoint.lengthY(endY_negative) / 20) * count;
        else
            return nilPoint.getY() + (nilPoint.lengthY(endY_positive) / 20) * count;
    }

    private void drawPoint(Point point){
        canvas.drawCircle(point.getX(), point.getY(), POINT_RADIUS, pointPaint);
    }

    private void drawAxises(){
        nilPoint = new Point(corners[1].getX() + 75, (int)(corners[3].getY() / 1.3));
        endX =  new Point(corners[3].getX() - 50, (int)(corners[3].getY() / 1.3));
        endY_positive = new Point(corners[1].getX() + 75, corners[1].getY() + 50);
        endY_negative = new Point(corners[0].getX() + 75, corners[0].getY() - 50);

        drawAxis(nilPoint, endX);
        drawAxis(nilPoint, endY_negative);
        drawAxis(nilPoint, endY_positive);

        canvas.drawText("0", nilPoint.getX() - 50, nilPoint.getY() + 25, textPaint);
        drawPoint(nilPoint);

        drawArrowX(endX);
        drawArrowY(endY_positive);
    }

    private void drawAxis(Point start, Point end){
        Log.i("tag", start.getX() + " " + start.getY() + "  " + getHeight());
        canvas.drawLine(start.getX(), start.getY(), end.getX(), end.getY(), axisPaint);
    }

    private void drawArrowX(Point point){
        Drawable arrowRight = getResources().getDrawable(R.drawable.arrow_axis_x);
        drawArrow(arrowRight, point, 70, 70);
    }

    private void drawArrowY(Point point){
        Drawable arrow = getResources().getDrawable(R.drawable.arrow_axis_y);
        drawArrow(arrow, point, 50, 50);
    }

    private void drawArrow(Drawable arrow, Point point, int xOffset, int yOffset){
        arrow.setAlpha(255);
        arrow.setBounds(point.getX() - xOffset, point.getY() - xOffset,point.getX() + xOffset,point.getY() + yOffset);
        arrow.draw(canvas);
    }

}
