package ru.otus.architect.commands;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.architect.game.objects.Boostable;
import ru.otus.architect.vector.PolarVector2DBuilder;
import ru.otus.architect.vector.Vector;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoostCommandTest {
    private final static int DISCRETE_COUNT = 180;
    private final static double TEST_ACCELERATION = 13;
    private final static Vector TEST_VELOCITY = PolarVector2DBuilder.builder(DISCRETE_COUNT)
            .x(12)
            .y(5)
            .build();
    private final static Vector RESULT_VELOCITY = PolarVector2DBuilder.builder(DISCRETE_COUNT)
            .x(24)
            .y(10)
            .build();


    @Mock
    private Boostable boostable;

    @Test
    void execute() {
        when(boostable.getVelocity()).thenReturn(TEST_VELOCITY);
        when(boostable.getAcceleration()).thenReturn(TEST_ACCELERATION);

        Command command = new BoostCommand(boostable);
        command.execute();

        verify(boostable, times(1)).setVelocity(RESULT_VELOCITY);
    }
}