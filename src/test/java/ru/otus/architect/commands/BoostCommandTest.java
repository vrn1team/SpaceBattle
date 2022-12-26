package ru.otus.architect.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.architect.game.objects.Accelerator;
import ru.otus.architect.vector.Vector;
import ru.otus.architect.vector.Vector2DBuilder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoostCommandTest {
    private final static int DISCRETE_COUNT = 180;
    private final static double TEST_ACCELERATION = 13;
    private final static Vector TEST_VELOCITY = Vector2DBuilder.builder(DISCRETE_COUNT)
            .x(12)
            .y(5)
            .build();
    private final static Vector RESULT_VELOCITY = Vector2DBuilder.builder(DISCRETE_COUNT)
            .x(24)
            .y(10)
            .build();

    @Mock
    private Accelerator accelerator;

    @Test
    void execute() {
        when(accelerator.getVelocity()).thenReturn(TEST_VELOCITY);
        when(accelerator.getAcceleration()).thenReturn(TEST_ACCELERATION);

        Command command = new BoostCommand(accelerator);
        command.execute();

        verify(accelerator, times(1)).setVelocity(RESULT_VELOCITY);
    }

    @Test
    void zeroBoost() {
        when(accelerator.getVelocity()).thenReturn(TEST_VELOCITY);
        when(accelerator.getAcceleration()).thenReturn(0.0);

        Command command = new BoostCommand(accelerator);
        command.execute();

        verify(accelerator, times(1)).setVelocity(TEST_VELOCITY);
    }

    @Test
    void boostImmobile() {
        doThrow(new RuntimeException()).when(accelerator).setVelocity(any());
        when(accelerator.getVelocity()).thenReturn(TEST_VELOCITY);
        when(accelerator.getAcceleration()).thenReturn(0.0);

        Command command = new BoostCommand(accelerator);

        Assertions.assertThrows(BoostCommandException.class, command::execute);
    }

    @Test
    void boostNoVelocity() {
        doThrow(new RuntimeException()).when(accelerator).getVelocity();

        Command command = new BoostCommand(accelerator);

        Assertions.assertThrows(BoostCommandException.class, command::execute);
    }

    @Test
    void boostNoAcceleration() {
        doThrow(new RuntimeException()).when(accelerator).getAcceleration();

        Command command = new BoostCommand(accelerator);

        Assertions.assertThrows(BoostCommandException.class, command::execute);
    }


}