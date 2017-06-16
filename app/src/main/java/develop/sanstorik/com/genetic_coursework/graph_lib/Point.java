package develop.sanstorik.com.genetic_coursework.graph_lib;

class Point {
    private final int x;
    private final int y;

    Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    int lengthX(Point secondPoint){
        return Math.abs(x - secondPoint.x);
    }

    int lengthY(Point secondPoint){
        return Math.abs(y - secondPoint.y);
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }
}
