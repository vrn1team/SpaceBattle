package ru.otus.architect.commands;

import ch.qos.logback.classic.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.architect.context.ApplicationContext;
import ru.otus.architect.exceptionHandling.ExceptionHandlerService;
import ru.otus.architect.exceptionHandling.handlers.CommandExceptionLogHandler;
import ru.otus.architect.exceptionHandling.handlers.CommandExceptionRetryHandler;
import ru.otus.architect.exceptionHandling.handlers.CommandExceptionSecondRetryHandler;

import java.util.LinkedList;
import java.util.Queue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TwiceRetryStrategyRegistrationCommandTest {
    @Spy
    private Queue<Command> commandQueue = new LinkedList<>();

    @Mock
    private Logger logger;
    @Mock
    private ExceptionHandlerService handlerService;

    @Mock
    private ApplicationContext context;

    private Command command;

    @BeforeEach
    void setUp() {
        when(context.getExceptionHandlerService()).thenReturn(handlerService);
        when(context.getCommandQueue()).thenReturn(commandQueue);
        when(context.getLogger()).thenReturn(logger);
        command = new TwiceRetryStrategyRegistrationCommand(Command.class, Exception.class, context);
    }

    @Test
    void execute() {
        command.execute();
        verify(handlerService, times(1))
                .registerHandlingRule(
                        any(),
                        any(),
                        any(CommandExceptionRetryHandler.class));
        verify(handlerService, times(2))
                .registerExceptionHandlingRule(any(), any());
        verify(handlerService, times(1))
                .registerExceptionHandlingRule(any(), any(CommandExceptionSecondRetryHandler.class));
        verify(handlerService, times(1))
                .registerExceptionHandlingRule(any(), any(CommandExceptionLogHandler.class));
    }
}