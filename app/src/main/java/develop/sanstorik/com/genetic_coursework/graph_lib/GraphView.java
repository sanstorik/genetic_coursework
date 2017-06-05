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

import java.util.Collections;
import java.util.List;
import java.util.Timer;

import develop.sanstorik.com.genetic_coursework.Genetic.Individual;
import develop.sanstorik.com.genetic_coursework.Genetic.Population;
import develop.sanstorik.com.genetic_coursework.R;

public class GraphView extends View {

    private enum Corners{
        BOTTOM_LEFT {
            @Override Point point() {
                return GraphView.corners[0];
            }
        },
        TOP_LEFT {
            @Override Point point() {
                return GraphView.corners[1];
            }
        },
        TOP_RIGHT {
            @Override Point point() {
                return GraphView.corners[2];
            }
        },
        BOTTOM_RIGHT {
            @Override Point point() {
                return GraphView.corners[3];
            }
        };

        abstract Point point();
    }

    private List<Individual> individuals;
    private static Point[] corners;

    private Paint pointPaint;
    private Paint innerPointPaint;
    private Paint axisPaint;
    private Paint textPaint;

    private Point nilPoint;
    private Point endX;
    private Point endY_positive;
    private Point endY_negative;

    private double minValY_pos;
    private double maxValY_pos;

    private final static int POINT_RADIUS = 10;
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
        pointPaint.setStyle(Paint.Style.STROKE);
        pointPaint.setStrokeWidth(5);

        innerPointPaint = new Paint(pointPaint);
        innerPointPaint.setStyle(Paint.Style.FILL);
        innerPointPaint.setColor(Color.LTGRAY);

        axisPaint = new Paint(pointPaint);
        axisPaint.setStyle(Paint.Style.FILL);
        axisPaint.setColor(Color.BLACK);
        axisPaint.setStrokeWidth(10);

        textPaint = new Paint(axisPaint);
        textPaint.setTextSize(50);
        textPaint.setColor(Color.BLUE);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        drawAxises();

        if (individuals == null || individuals.size() == 0) {
            drawText("List is null or empty", nilPoint, nilPoint.lengthX(endX) / 2, -nilPoint.lengthY(endY_positive) / 2);
            return;
        }

        //we need this to get graphic [min;max] instead of
        minValY_pos = Collections.min(individuals).getFunctionValue();
        maxValY_pos = Collections.max(individuals).getFunctionValue();


        drawText(String.valueOf(minValY_pos), nilPoint, 0, 55);
        drawText(String.valueOf(maxValY_pos),
                new Point(nilPoint.getX(), virtualPointY(maxValY_pos - (minValY_pos))), 0, 0);

        double scale = 1 - (minValY_pos/maxValY_pos);


        int index = 1;
        for(Individual ind: individuals){
            Log.i("tag", ind.getFunctionValue()+"");
            Point point = new Point(virtualPointX(index++), virtualPointY(ind.getFunctionValue() - minValY_pos));
            drawPoint(point);
        }
    }

    /*
    @param count get needed point on X axis
    @returns this point, recalculated to scale
     */
    private int virtualPointX(double count){
        return (int)(nilPoint.getX() + (nilPoint.lengthX(endX) / (individuals.size() + 2)) * count);
    }

    private int virtualPointY(double count){
        if(count < 0)
            return (int)(nilPoint.getY() - (nilPoint.lengthY(endY_negative) / 35) * count);
        else
            return (int)(nilPoint.getY() - (nilPoint.lengthY(endY_positive) / (maxValY_pos - (minValY_pos * 0.8))) * count);
    }

    private void drawPoint(Point point){
        canvas.drawCircle(point.getX(), point.getY(), POINT_RADIUS * 0.9f, innerPointPaint);
        canvas.drawCircle(point.getX(), point.getY(), POINT_RADIUS, pointPaint);
    }

    private void drawLine(Point start, Point end){
        canvas.drawLine(start.getX(), start.getY(), end.getX(), end.getY(), textPaint);
    }

    private void drawText(String text, Point point, int offsetX, int offsetY){
        canvas.drawText(text, point.getX() + offsetX, point.getY() + offsetY, textPaint);
    }

    private void drawAxises(){
        if(nilPoint == null) {
            //to avoid allocation every time view is drawn
            nilPoint = new Point(Corners.BOTTOM_LEFT.point().getX() + 75, (int) (Corners.BOTTOM_RIGHT.point().getY() / 1.3));
            endX = new Point(Corners.BOTTOM_RIGHT.point().getX() - 50, nilPoint.getY());
            endY_positive = new Point(Corners.TOP_LEFT.point().getX() + 75, Corners.TOP_LEFT.point().getY() + 50);
            endY_negative = new Point(Corners.BOTTOM_LEFT.point().getX() + 75, Corners.BOTTOM_LEFT.point().getY() - 50);
        }

        drawAxis(nilPoint, endX);
        drawAxis(nilPoint, endY_negative);
        drawAxis(nilPoint, endY_positive);
        drawPoint(nilPoint);

        drawArrowX(endX);
        drawArrowY(endY_positive);
    }

    private void drawAxis(Point start, Point end){
        canvas.drawLine(start.getX(), start.getY(), end.getX(), end.getY(), axisPaint);
    }

    private void drawArrowX(Point point){
        Drawable arrowRight = getResources().getDrawable(R.drawable.arrow_axis_x);
        drawArrow(arrowRight, point, 70, 70);

        drawText("i", point, 20, 50);
    }

    private void drawArrowY(Point point){
        Drawable arrow = getResources().getDrawable(R.drawable.arrow_axis_y);
        drawArrow(arrow, point, 50, 50);

        drawText("Y", point, -60, 20);
    }

    private void drawArrow(Drawable arrow, Point point, int xOffset, int yOffset){
        arrow.setAlpha(255);
        arrow.setBounds(point.getX() - xOffset, point.getY() - xOffset,point.getX() + xOffset,point.getY() + yOffset);
        arrow.draw(canvas);
    }

}
