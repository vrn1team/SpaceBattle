package ru.otus.architect.vector;

import java.util.ArrayList;
import java.util.List;

public class PolarVector2D implements Vector {

    private final static double DELTA = 0.05;
    private final static int DIMENSION = 2;
    private final int discreteAngleCount;
    private final double radial;
    private final int corner;

    public PolarVector2D(double radial, int corner, int discreteAngleCount) {
        if (discreteAngleCount <= 0 ) {
            throw new VectorsInitiationException("Bad discreteAngleCount");
        }
        if (radial <  - DELTA ) {
            throw new VectorsInitiationException("Bad radial");
        }
        this.discreteAngleCount = discreteAngleCount;
        this.radial = radial;
        this.corner = corner;
    }

    @Override
    public Vector vectorAdd(Vector vector) {
        if (vector.getDimension() != this.getDimension()) {
            throw new VectorsDimensionException();
        }
        List<Integer> result = addCoordinates(vector.getCoordinates());
        return Vector2DBuilder.builder(discreteAngleCount).x(result.get(0)).y(result.get(1)).build();
    }

    @Override
    public Vector scalarAdd(double acceleration) {
        return new PolarVector2D(radial + acceleration, corner, discreteAngleCount);
    }

    @Override
    public List<Integer> getCoordinates() {
        return List.of((int) Math.round(radial*Math.cos(getAngle())), (int) Math.round(radial*Math.sin(getAngle())));
    }

    @Override
    public int getDimension() {
        return DIMENSION;
    }

    private double getAngle() {
        return (Math.PI*corner)/discreteAngleCount;
    }

    private List<Integer> addCoordinates(List<Integer> velocity) {
        List<Integer> result = new ArrayList<>();
        List<Integer> coordinates = getCoordinates();
        for (var i = 0; i < coordinates.size(); i++) {
            result.add(i, coordinates.get(i) + velocity.get(i));
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PolarVector2D that = (PolarVector2D) o;

        if (this.getDimension() != that.getDimension() ) return false;

        var thatCoordinates = that.getCoordinates();
        var thisCoordinates = this.getCoordinates();

        for (var i = 0; i < getDimension(); i ++) {
            if (!thatCoordinates.get(i).equals(thisCoordinates.get(i))) {
                return false;
            }
        }
        return true;
    }
}
