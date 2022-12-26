package ru.otus.architect.vector;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VectorImplTest {
    private final static List<Integer> TEST_COORDINATES_1 = List.of(12, 5);
    private final static List<Integer> TEST_COORDINATES_2 = List.of(-5, 2);
    private final static List<Integer> TEST_SUM_RESULT = List.of(7, 7);
    private final static List<Integer> BAD_TEST_COORDINATES = List.of(-5, 2, 0);

    @Test
    void add() {
        Vector vector1 = new VectorImpl(TEST_COORDINATES_1);
        Vector vector2 = new VectorImpl(TEST_COORDINATES_2);

        List<Integer> result = vector1.vectorAdd(vector2).getCoordinates();

        for (var i = 0; i < vector1.getDimension(); i++) {
            assertEquals(result.get(i), TEST_SUM_RESULT.get(i));
        }
    }

    @Test
    void addBadDimension() {
        Vector vector1 = new VectorImpl(TEST_COORDINATES_1);
        Vector badVector = new VectorImpl(BAD_TEST_COORDINATES);

        assertThrows(VectorsDimensionException.class, () -> vector1.vectorAdd(badVector));
    }

    @Test
    void getCoordinates() {
        Vector vector1 = new VectorImpl(TEST_COORDINATES_1);

        List<Integer> result = vector1.getCoordinates();

        for (var i = 0; i < vector1.getDimension(); i++) {
            assertEquals(result.get(i), TEST_COORDINATES_1.get(i));
        }
    }

    @Test
    void getCoordinatesIsConcurrencySafe() {
        Vector vector1 = new VectorImpl(TEST_COORDINATES_1);

        List<Integer> coordinates = vector1.getCoordinates();
        coordinates.add(0, 10);
        coordinates.add(0);

        assertEquals(TEST_COORDINATES_1.size(), vector1.getDimension());
        List<Integer> result = vector1.getCoordinates();
        for (var i = 0; i < vector1.getDimension(); i++) {
            assertEquals(result.get(i), TEST_COORDINATES_1.get(i));
        }
    }

    @Test
    void getDimension() {
        Vector vector1 = new VectorImpl(TEST_COORDINATES_1);

        assertEquals(TEST_COORDINATES_1.size(), vector1.getDimension());
    }

    @Test
    void scalarAdd() {
        Vector vector1 = new VectorImpl(TEST_COORDINATES_1);

        List<Integer> result = vector1.scalarAdd(13).getCoordinates();

        for (var i = 0; i < vector1.getDimension(); i++) {
            assertEquals(2 * TEST_COORDINATES_1.get(i), result.get(i));
        }
    }
}