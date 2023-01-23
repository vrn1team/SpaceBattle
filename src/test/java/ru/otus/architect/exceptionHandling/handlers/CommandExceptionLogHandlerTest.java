package ru.otus.architect.exceptionHandling.handlers;

import ch.qos.logback.classic.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.architect.commands.Command;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandExceptionLogHandlerTest {
    @Mock
    private Logger log;
    @Mock
    private Command command;
    @Mock
    private Exception exception;
    @Spy
    Queue<Command> commandQueue = new LinkedList<>();

    private CommandExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new CommandExceptionLogHandler(log, commandQueue);
    }

    @Test
    @DisplayName("Проверяем, что команда добавляется в очередь")
    void testHandleException() {
        exceptionHandler.handleException(command, exception);
        verify(commandQueue, times(1)).offer(any());
    }

    @Test
    @DisplayName("В случае если добавить в очередь нельзя, выбрасывается исключение")
    void testWhenCanNotOfferCommand() {
        when(commandQueue.offer(any())).thenReturn(false);
        assertThrows(RuntimeException.class, () -> exceptionHandler.handleException(command, exception));
    }
}