package ru.otus.architect.vector;

import org.junit.jupiter.api.Test;
import ru.otus.architect.angle.Angle;
import ru.otus.architect.angle.AngleImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PolarVector2DTest {
    private final static int DISCRETE_COUNT = 180;
    private final static int BAD_DISCRETE_COUNT = 0;
    private final static int TEST_X = 12;
    private final static int TEST_Y = 5;
    private final static double TEST_CORNER = 13;
    private final static double BAD_CORNER = -0.5;
    private final static Angle TEST_ANGLE = new AngleImpl(23, DISCRETE_COUNT);
    private final static List<Integer> TEST_COORDINATES_1 = List.of(12, 5);
    private final static List<Integer> TEST_COORDINATES_2 = List.of(-5, 2);
    private final static List<Integer> TEST_SUM_RESULT = List.of(7, 7);
    private final static List<Integer> BAD_TEST_COORDINATES = List.of(-5, 2, 0);

    @Test
    void createByPolarCoordinates() {
        Vector vector = new PolarVector2D(TEST_CORNER, TEST_ANGLE);

        List<Integer> result = vector.getCoordinates();

        for (var i = 0; i < vector.getDimension(); i++) {
            assertEquals(TEST_COORDINATES_1.get(i), result.get(i));
        }
    }

    @Test
    void createByPolarCoordinatesWithBadRadial() {
        assertThrows(VectorsInitiationException.class, () -> new PolarVector2D(BAD_CORNER, TEST_ANGLE));
    }

    @Test
    void createByPolarCoordinatesWithBadDiscreteAngleCount() {
        assertThrows(RuntimeException.class, () -> new PolarVector2D(BAD_CORNER, new AngleImpl(0, BAD_DISCRETE_COUNT)));
    }

    @Test
    void addBadDimension() {
        Vector vector1 = Vector2DBuilder.builder().x(TEST_X).y(TEST_Y).build();
        Vector badVector = new VectorImpl(BAD_TEST_COORDINATES);

        assertThrows(VectorsDimensionException.class, () -> vector1.vectorAdd(badVector));
    }

    @Test
    void add() {
        Vector vector1 = Vector2DBuilder.builder()
                .x(TEST_COORDINATES_1.get(0))
                .y(TEST_COORDINATES_1.get(1))
                .build();
        Vector vector2 = Vector2DBuilder.builder()
                .x(TEST_COORDINATES_2.get(0))
                .y(TEST_COORDINATES_2.get(1))
                .build();

        List<Integer> result = vector1.vectorAdd(vector2).getCoordinates();

        for (var i = 0; i < vector1.getDimension(); i++) {
            assertEquals(result.get(i), TEST_SUM_RESULT.get(i));
        }
    }

    @Test
    void getCoordinates() {
        Vector vector = Vector2DBuilder.builder()
                .x(TEST_COORDINATES_1.get(0))
                .y(TEST_COORDINATES_1.get(1))
                .build();

        List<Integer> result = vector.getCoordinates();

        for (var i = 0; i < vector.getDimension(); i++) {
            assertEquals(result.get(i), TEST_COORDINATES_1.get(i));
        }
    }

    @Test
    void getCoordinatesIsConcurrencySafe() {
        Vector vector1 = Vector2DBuilder.builder()
                .x(TEST_COORDINATES_1.get(0))
                .y(TEST_COORDINATES_1.get(1))
                .build();

        List<Integer> coordinates = vector1.getCoordinates();

        assertThrows(UnsupportedOperationException.class, () -> coordinates.add(0, 10));
        assertThrows(UnsupportedOperationException.class, () -> coordinates.add(10));
        assertEquals(TEST_COORDINATES_1.size(), vector1.getDimension());
    }

    @Test
    void getDimension() {
        Vector vector = Vector2DBuilder.builder()
                .x(TEST_COORDINATES_1.get(0))
                .y(TEST_COORDINATES_1.get(1))
                .build();

        assertEquals(TEST_COORDINATES_1.size(), vector.getDimension());
    }

    @Test
    void scalarAdd() {
        Vector vector1 = Vector2DBuilder.builder()
                .x(TEST_COORDINATES_1.get(0))
                .y(TEST_COORDINATES_1.get(1))
                .build();

        List<Integer> result = vector1.scalarAdd(13).getCoordinates();

        for (var i = 0; i < vector1.getDimension(); i++) {
            assertEquals(2 * TEST_COORDINATES_1.get(i), result.get(i));
        }
    }

    @Test
    void testEquals() {
        assertEquals(
                Vector2DBuilder.builder()
                        .x(TEST_COORDINATES_1.get(0))
                        .y(TEST_COORDINATES_1.get(1))
                        .build(),
                Vector2DBuilder.builder()
                        .x(TEST_COORDINATES_1.get(0))
                        .y(TEST_COORDINATES_1.get(1))
                        .build());

        assertNotEquals(
                Vector2DBuilder.builder()
                        .x(TEST_COORDINATES_1.get(0))
                        .y(TEST_COORDINATES_1.get(1))
                        .build(),
                Vector2DBuilder.builder()
                        .x(TEST_COORDINATES_2.get(0))
                        .y(TEST_COORDINATES_2.get(1))
                        .build());
    }
}