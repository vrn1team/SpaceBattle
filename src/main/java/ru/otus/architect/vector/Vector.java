package ru.otus.architect.vector;

import java.util.List;

public interface Vector {

    Vector vectorAdd(Vector vector);

    Vector scalarAdd(double acceleration);

    List<Integer> getCoordinates();

    int getDimension();
}
