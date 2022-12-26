package ru.otus.architect.vector;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Vector2DBuilderTest {
    private final static int DISCRETE_COUNT = 180;
    private final static List<Integer> TEST_COORDINATES_1 = List.of(12, 5);
    private final static List<Integer> TEST_COORDINATES_2 = List.of(-5, 2);
    private final static List<Integer> TEST_ZERO_COORDINATES = List.of(0, 0);
    private final static List<Integer> TEST_X_COORDINATES = List.of(1, 0);
    private final static List<Integer> TEST_Y_COORDINATES = List.of(0, 1);

    @Test
    void createByCoordinates() {
        Vector vector = Vector2DBuilder.builder(DISCRETE_COUNT)
                .x(TEST_COORDINATES_1.get(0))
                .y(TEST_COORDINATES_1.get(1)).build();

        List<Integer> result = vector.getCoordinates();

        for (var i = 0; i < vector.getDimension(); i++) {
            assertEquals(TEST_COORDINATES_1.get(i), result.get(i));
        }

        Vector vector2 = Vector2DBuilder.builder(DISCRETE_COUNT)
                .x(TEST_COORDINATES_2.get(0))
                .y(TEST_COORDINATES_2.get(1)).build();

        result = vector2.getCoordinates();

        for (var i = 0; i < vector.getDimension(); i++) {
            assertEquals(TEST_COORDINATES_2.get(i), result.get(i));
        }
    }

    @Test
    void createByZeroCoordinates() {
        Vector vector = Vector2DBuilder.builder(DISCRETE_COUNT)
                .x(TEST_ZERO_COORDINATES.get(0))
                .y(TEST_ZERO_COORDINATES.get(1)).build();

        List<Integer> result = vector.getCoordinates();

        for (var i = 0; i < vector.getDimension(); i++) {
            assertEquals(TEST_ZERO_COORDINATES.get(i), result.get(i));
        }
    }

    @Test
    void createByXZeroCoordinates() {
        Vector vector = Vector2DBuilder.builder(DISCRETE_COUNT)
                .x(TEST_X_COORDINATES.get(0))
                .y(TEST_X_COORDINATES.get(1)).build();

        List<Integer> result = vector.getCoordinates();

        for (var i = 0; i < vector.getDimension(); i++) {
            assertEquals(TEST_X_COORDINATES.get(i), result.get(i));
        }
    }

    @Test
    void createByYZeroCoordinates() {
        Vector vector = Vector2DBuilder.builder(DISCRETE_COUNT)
                .x(TEST_Y_COORDINATES.get(0))
                .y(TEST_Y_COORDINATES.get(1)).build();

        List<Integer> result = vector.getCoordinates();

        for (var i = 0; i < vector.getDimension(); i++) {
            assertEquals(TEST_Y_COORDINATES.get(i), result.get(i));
        }
    }
}