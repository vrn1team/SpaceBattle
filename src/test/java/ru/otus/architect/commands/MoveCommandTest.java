package ru.otus.architect.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.architect.game.objects.Mobile;
import ru.otus.architect.vector.Vector;
import ru.otus.architect.vector.Vector2DBuilder;
import ru.otus.architect.vector.VectorImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MoveCommandTest {
    private final static int DISCRETE_COUNT = 180;
    private final static Vector TEST_COORDINATES_1 = Vector2DBuilder.builder(DISCRETE_COUNT)
            .x(12)
            .y(5)
            .build();
    private final static Vector TEST_COORDINATES_2 = Vector2DBuilder.builder(DISCRETE_COUNT)
            .x(-7)
            .y(3)
            .build();

    private final static Vector BAD_COORDINATES = new VectorImpl(-5, 2, 0);

    private final static Vector TEST_SUM_RESULT = Vector2DBuilder.builder(DISCRETE_COUNT)
            .x(5)
            .y(8)
            .build();
    @Mock
    private Mobile mobile;

    @BeforeEach
    void setUp() {
        when(mobile.getPosition())
                .thenReturn(TEST_COORDINATES_1);
        when(mobile.getVelocity())
                .thenReturn(TEST_COORDINATES_2);
    }

    @Test
    void execute() {
        Command command = new MoveCommand(mobile);
        command.execute();

        verify(mobile, times(1)).setPosition(TEST_SUM_RESULT);
    }

    @Test
    void moveImmobile() {
        doThrow(new RuntimeException())
                .when(mobile).setPosition(any());

        Command command = new MoveCommand(mobile);
        assertThrows(MoveCommandException.class, command::execute);
    }

    @Test
    void badVelocity() {
        when(mobile.getVelocity())
                .thenReturn(BAD_COORDINATES);

        Command command = new MoveCommand(mobile);

        assertThrows(MoveCommandException.class, command::execute);
    }

    @Test
    void badCoordinates() {
        when(mobile.getPosition())
                .thenReturn(BAD_COORDINATES);

        Command command = new MoveCommand(mobile);

        assertThrows(MoveCommandException.class, command::execute);
    }

}