package ru.otus.architect.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.architect.TestLog4j2Appender;
import ru.otus.architect.commands.Command;
import ru.otus.architect.commands.LogCommand;
import ru.otus.architect.commands.MoveCommand;
import ru.otus.architect.commands.RetryCommand;
import ru.otus.architect.exceptions.ExceptionHandler;
import ru.otus.architect.exceptions.ReadPositionException;
import ru.otus.architect.exceptions.impl.GameExceptionHandler;
import ru.otus.architect.stubs.DoesNotAllowReadPositionStub;
import ru.otus.architect.stubs.MovableStub;

import java.util.ArrayDeque;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class GameTest {

    private static final String TEST_COMMAND = "Commands.TestCommand";

    @Mock
    ExceptionHandler exceptionHandler;

    @Mock
    Command testCommand;

    @Test
    @DisplayName("Очередь команд пуста после выполнения, ошибок нет")
    void testGameCompletes() {
        var commands = new ArrayDeque<Command>();
        commands.add(new MoveCommand(new MovableStub()));
        // act
        var cut = new Game(commands, exceptionHandler);
        cut.run();
        // assert
        assertTrue(commands.isEmpty());
    }

    @Test
    @DisplayName("Исключение должно быть обработано хендлером")
    void testGameHandleTheException() {
        // arrange
        var commands = new ArrayDeque<Command>();
        commands.add(new MoveCommand(new DoesNotAllowReadPositionStub()));
        ArgumentCaptor<Exception> valueCapture = ArgumentCaptor.forClass(Exception.class);
        doNothing().when(exceptionHandler).handle(any(Command.class), valueCapture.capture());
        var cut = new Game(commands, exceptionHandler);

        // act
        cut.run();

        // assert
        assertEquals(ReadPositionException.class, valueCapture.getValue().getClass());
        assertTrue(commands.isEmpty());
    }

    @Test
    @DisplayName("Исключение должно быть обработано хендлером и попасть в лог")
    void testGameHandleAndLogTheException() {
        // arrange
        var commands = new ArrayDeque<Command>();
        doThrow(new ReadPositionException("ERROR")).when(testCommand).execute();
        when(testCommand.getName()).thenReturn(TEST_COMMAND);
        commands.add(testCommand);

        // Exception handler
        var handler = new GameExceptionHandler(
                Map.of(TEST_COMMAND, Map.of(ReadPositionException.class, LogCommand.class)),
                commands
        );
        var handlerSpy = spy(handler);

        // Argument capture
        ArgumentCaptor<Exception> valueCapture = ArgumentCaptor.forClass(Exception.class);
        doCallRealMethod().when(handlerSpy).handle(any(Command.class), valueCapture.capture());

        // Log capture
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();
        var appender = TestLog4j2Appender.createAppender("TestAppender", null, null, null);
        assert appender != null;
        appender.start();
        config.addAppender(appender);
        Logger logger = LogManager.getLogger(testCommand.getClass());
        ((org.apache.logging.log4j.core.Logger) logger).addAppender(appender);
        ctx.updateLoggers();

        var cut = new Game(commands, handlerSpy);

        // act
        cut.run();

        // assert
        assertEquals(ReadPositionException.class, valueCapture.getValue().getClass());
        assertTrue(commands.isEmpty());
        assertTrue(appender.getMessage(0).length() > 0);
    }

    @Test
    @DisplayName("Исключение должно быть обработано хендлером и команда должна быть повторена")
    void testGameHandleAndRetry() {
        //arrange
        var commands = new ArrayDeque<Command>();
        doThrow(new ReadPositionException("ERROR")).when(testCommand).execute();
        when(testCommand.getName()).thenReturn(TEST_COMMAND);
        commands.add(testCommand);

        var handler = new GameExceptionHandler(
                Map.of(TEST_COMMAND, Map.of(ReadPositionException.class, RetryCommand.class)),
                commands
        );
        var handlerSpy = spy(handler);

        ArgumentCaptor<Exception> valueCapture = ArgumentCaptor.forClass(Exception.class);
        doCallRealMethod().when(handlerSpy).handle(any(Command.class), valueCapture.capture());

        var cut = new Game(commands, handlerSpy);

        // act
        cut.run();

        // assert
        assertEquals(ReadPositionException.class, valueCapture.getValue().getClass());
        assertTrue(commands.isEmpty());
        verify(testCommand, times(2)).execute();
    }
}
