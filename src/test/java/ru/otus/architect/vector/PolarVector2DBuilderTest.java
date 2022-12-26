package ru.otus.architect.vector;

import org.junit.jupiter.api.Test;
import ru.otus.architect.vector.PolarVector2DBuilder;
import ru.otus.architect.vector.Vector;
import ru.otus.architect.vector.VectorsInitiationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PolarVector2DBuilderTest {
    private final static int DISCRETE_COUNT = 180;
    private final static List<Integer> TEST_COORDINATES_1 = List.of(12, 5);
    private final static List<Integer> TEST_COORDINATES_2 = List.of(-5, 2);
    private final static List<Integer> BAD_TEST_COORDINATES = List.of(0, 0);


    @Test
    void createByCoordinates() {
        Vector vector = PolarVector2DBuilder.builder(DISCRETE_COUNT)
                .x(TEST_COORDINATES_1.get(0))
                .y(TEST_COORDINATES_1.get(1)).build();

        List<Integer> result = vector.getCoordinates();

        for (var i = 0; i < vector.getDimension(); i++) {
            assertEquals(TEST_COORDINATES_1.get(i), result.get(i));
        }

        Vector vector2 = PolarVector2DBuilder.builder(DISCRETE_COUNT)
                .x(TEST_COORDINATES_2.get(0))
                .y(TEST_COORDINATES_2.get(1)).build();

        result = vector2.getCoordinates();

        for (var i = 0; i < vector.getDimension(); i++) {
            assertEquals(TEST_COORDINATES_2.get(i), result.get(i));
        }
    }

    @Test
    void badCreateTest() {
        assertThrows(VectorsInitiationException.class, () -> PolarVector2DBuilder.builder(DISCRETE_COUNT)
                .x(BAD_TEST_COORDINATES.get(0))
                .y(BAD_TEST_COORDINATES.get(1))
                .build());
    }
}