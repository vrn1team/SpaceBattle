package ru.otus.architect.commands;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.architect.game.objects.Moveable;
import ru.otus.architect.vector.PolarVector2DBuilder;
import ru.otus.architect.vector.Vector;
import ru.otus.architect.vector.VectorImpl;
import ru.otus.architect.vector.VectorsDimensionException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MoveCommandTest {
    private final static int DISCRETE_COUNT = 180;
    private final static Vector TEST_COORDINATES_1 = PolarVector2DBuilder.builder(DISCRETE_COUNT)
            .x(12)
            .y(5)
            .build();
    private final static Vector TEST_COORDINATES_2 = PolarVector2DBuilder.builder(DISCRETE_COUNT)
            .x(-5)
            .y(2)
            .build();

    private final static Vector BAD_COORDINATES = new VectorImpl(-5, 2, 0);

    private final static Vector TEST_SUM_RESULT = PolarVector2DBuilder.builder(DISCRETE_COUNT)
            .x(7)
            .y(7)
            .build();
    @Mock
    private Moveable moveable;

    @Test
    void execute() {
        Mockito.when(moveable.getPosition())
                .thenReturn(TEST_COORDINATES_1);
        Mockito.when(moveable.getVelocity())
                .thenReturn(TEST_COORDINATES_2);

        Command command = new MoveCommand(moveable);
        command.execute();

        verify(moveable, times(1)).setPosition(TEST_SUM_RESULT);
    }

    @Test
    void badCoordinates() {
        Mockito.when(moveable.getPosition())
                .thenReturn(TEST_COORDINATES_1);
        Mockito.when(moveable.getVelocity())
                .thenReturn(BAD_COORDINATES);

        Command command = new MoveCommand(moveable);

        assertThrows(VectorsDimensionException.class, command::execute);
    }

}