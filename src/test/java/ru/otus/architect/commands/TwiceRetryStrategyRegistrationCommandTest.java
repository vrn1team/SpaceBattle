package ru.otus.architect.commands;

import ch.qos.logback.classic.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.architect.exceptionHandling.ExceptionHandlerService;

import java.util.LinkedList;
import java.util.Queue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TwiceRetryStrategyRegistrationCommandTest {
    @Spy
    private Queue<Command> commandQueue = new LinkedList<>();
    @Mock
    private Logger logger;
    @Mock
    private ExceptionHandlerService handlerService;

    private Command command;

    @BeforeEach
    void setUp() {
        command = new TwiceRetryStrategyRegistrationCommand(handlerService, commandQueue, logger);
    }

    @Test
    void execute() {
        command.execute();
        verify(handlerService, times(1))
                .registerHandlingRule(any());
        verify(handlerService, times(2))
                .registerExceptionHandlingRule(any(), any());
    }
}