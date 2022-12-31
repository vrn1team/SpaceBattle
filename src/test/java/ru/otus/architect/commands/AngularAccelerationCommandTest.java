package ru.otus.architect.commands;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.architect.angle.Angle;
import ru.otus.architect.angle.AngleImpl;
import ru.otus.architect.game.objects.AngularAccelerator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AngularAccelerationCommandTest {

    private final static Angle TEST_ANGLE_1 = new AngleImpl(4, 18);
    private final static Angle TEST_ANGLE_2 = new AngleImpl(50, 180);
    private final static Angle TEST_SUM_RESULT = new AngleImpl(9, 18);


    @Mock
    private AngularAccelerator angularAccelerator;

    @Test
    void execute() {
        when(angularAccelerator.getAngularVelocity())
                .thenReturn(TEST_ANGLE_1);
        when(angularAccelerator.getAngularAcceleration())
                .thenReturn(TEST_ANGLE_2);

        Command command = new AngularAccelerationCommand(angularAccelerator);
        command.execute();

        verify(angularAccelerator, times(1)).setAngularVelocity(TEST_SUM_RESULT);
    }

    @Test
    void badAngularVelocity() {
        doThrow(new RuntimeException())
                .when(angularAccelerator).getAngularVelocity();

        Command command = new AngularAccelerationCommand(angularAccelerator);

        assertThrows(AngularAccelerationCommandException.class, command::execute);
    }

    @Test
    void badAngularAcceleration() {
        when(angularAccelerator.getAngularVelocity())
                .thenReturn(TEST_ANGLE_1);
        doThrow(new RuntimeException())
                .when(angularAccelerator).getAngularAcceleration();

        Command command = new AngularAccelerationCommand(angularAccelerator);

        assertThrows(AngularAccelerationCommandException.class, command::execute);
    }

    @Test
    void accelerateNoAngularAccelerator() {
        when(angularAccelerator.getAngularVelocity())
                .thenReturn(TEST_ANGLE_1);
        when(angularAccelerator.getAngularAcceleration())
                .thenReturn(TEST_ANGLE_2);
        doThrow(new RuntimeException())
                .when(angularAccelerator).setAngularVelocity(any());

        Command command = new AngularAccelerationCommand(angularAccelerator);

        assertThrows(AngularAccelerationCommandException.class, command::execute);
    }
}