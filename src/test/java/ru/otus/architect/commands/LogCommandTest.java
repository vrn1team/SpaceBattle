package ru.otus.architect.commands;

import ch.qos.logback.classic.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LogCommandTest {
    private final static String MESSAGE = "Command {} complete with Exception {}";

    @Mock
    private Logger log;
    @Mock
    private Command command;
    @Mock
    private Exception exception;

    private Command logCommand;


    @BeforeEach
    void setUp() {
        logCommand = new LogCommand(log, command, exception);
    }

    @Test
    @DisplayName("Проверяем, что логирование проходит")
    void execute() {
        logCommand.execute();
        verify(log, times(1)).info(MESSAGE, command, exception);
    }
}