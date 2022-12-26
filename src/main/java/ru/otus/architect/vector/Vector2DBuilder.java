package ru.otus.architect.vector;

public class Vector2DBuilder {
    private final int discreteAngleCount;
    private int x;
    private int y;

    public Vector2DBuilder(int discreteAngleCount) {
        this.discreteAngleCount = discreteAngleCount;
    }

    public static Vector2DBuilder builder(int discreteAngleCount) {
        return new Vector2DBuilder(discreteAngleCount);
    }

    public Vector2DBuilder x(int x) {
        this.x = x;
        return this;
    }

    public Vector2DBuilder y(int y) {
        this.y = y;
        return this;
    }

    public Vector build() {
        double radial = Math.sqrt(x*x + y*y);
        int corner;
        if(0 == x) {
            if(0 == y) {
                return new VectorImpl(x, y);
            }
            corner = (y > 0)? discreteAngleCount/2: 3*discreteAngleCount/2;
        } else {
            corner = (int) Math.round(discreteAngleCount*Math.atan(1.0 *y/x)/Math.PI);
            if (x < 0) {
                corner += discreteAngleCount;
            }
        }
        return new PolarVector2D(radial, corner, discreteAngleCount);
    }
}
