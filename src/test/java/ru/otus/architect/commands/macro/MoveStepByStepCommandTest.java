package ru.otus.architect.commands.macro;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.architect.commands.Command;
import ru.otus.architect.exceptions.CommandException;
import ru.otus.architect.exceptions.ExceptionHandlingStrategy;
import ru.otus.architect.exceptions.handlers.StepByStepCommandExceptionHandler;
import ru.otus.architect.game.objects.dimension.Coordinates;
import ru.otus.architect.stubs.GameStub;
import ru.otus.architect.stubs.LimitedMovableStub;
import ru.otus.architect.stubs.MovableStub;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MoveStepByStepCommandTest {

    @Mock
    ExceptionHandlingStrategy exceptionHandlingStrategy;

    private Queue<Command> commands;

    @BeforeEach
    void init() {
        commands = new ArrayDeque<>();
    }

    @AfterEach
    void destroy() {
        commands.clear();
    }

    @Test
    @DisplayName("Команда непрерывного движения перемещает объект на точку 10,10 шагами по 1")
    void testExecute() {
        // arrange
        var movable = new MovableStub();
        var steps = 10;
        movable.setVelocity(new Coordinates(1, 1));
        var commandsQueue = spy(commands);
        var command = spy(new MoveStepByStepCommand(movable, steps, commandsQueue));
        commandsQueue.add(command);
        var game = new GameStub(commandsQueue, exceptionHandlingStrategy);
        //act
        game.run();
        // assert
        assertTrue(commandsQueue.isEmpty());
        assertEquals(new Coordinates(10, 10), movable.getPosition());
        verify(commandsQueue, Mockito.times(10)).offer(any(Command.class));
    }

    // TODO ?? how to test stopping in this case???
    @Test
    @DisplayName("Команда непрерывного движения ломается на 5 шаге, движение дальше не происходит")
    void testExecuteWithException() {
        // arrange
        var movable = new LimitedMovableStub();
        movable.setLimit(new Coordinates(5, 5)); // Exception after going beyond these coordinates
        var steps = 10;
        movable.setVelocity(new Coordinates(1, 1));
        when(exceptionHandlingStrategy.getHandler(any(), any())).thenReturn(new StepByStepCommandExceptionHandler(commands));
        var commandsQueue = spy(commands);
        var command = spy(new MoveStepByStepCommand(movable, steps, commandsQueue));
        commandsQueue.add(command);
        var game = new GameStub(commandsQueue, exceptionHandlingStrategy);
        //act
        assertThrows(CommandException.class, () -> game.run());
        // assert
        assertFalse(commandsQueue.isEmpty());
        assertEquals(new Coordinates(5, 5), movable.getPosition()); // only within limit
        verify(commandsQueue, Mockito.times(10)).offer(any(Command.class));
    }
}