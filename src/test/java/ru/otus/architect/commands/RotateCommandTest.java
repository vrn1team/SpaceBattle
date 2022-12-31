package ru.otus.architect.commands;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.architect.angle.Angle;
import ru.otus.architect.angle.AngleImpl;
import ru.otus.architect.game.objects.Rotation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RotateCommandTest {
    private final static Angle TEST_ANGLE_1 = new AngleImpl(4, 18);
    private final static Angle TEST_ANGLE_2 = new AngleImpl(50, 180);
    private final static Angle TEST_SUM_RESULT = new AngleImpl(9, 18);

    @Mock
    private Rotation rotation;

    @Test
    void execute() {
        when(rotation.getAngle())
                .thenReturn(TEST_ANGLE_1);
        when(rotation.getAngularVelocity())
                .thenReturn(TEST_ANGLE_2);

        Command command = new RotateCommand(rotation);
        command.execute();

        verify(rotation, times(1)).setAngle(TEST_SUM_RESULT);
    }

    @Test
    void badAngle () {
        doThrow(new RuntimeException())
                .when(rotation).getAngle();

        Command command = new RotateCommand(rotation);

        assertThrows(RotateCommandException.class, command::execute);
    }

    @Test
    void badAngularVelocity() {
        when(rotation.getAngle())
                .thenReturn(TEST_ANGLE_1);
        doThrow(new RuntimeException())
                .when(rotation).getAngularVelocity();

        Command command = new RotateCommand(rotation);

        assertThrows(RotateCommandException.class, command::execute);
    }

    @Test
    void rotateNotRotatable() {
        when(rotation.getAngle())
                .thenReturn(TEST_ANGLE_1);
        when(rotation.getAngularVelocity())
                .thenReturn(TEST_ANGLE_2);
        doThrow(new RuntimeException())
                .when(rotation).setAngle(any());

        Command command = new RotateCommand(rotation);

        assertThrows(RotateCommandException.class, command::execute);
    }
}