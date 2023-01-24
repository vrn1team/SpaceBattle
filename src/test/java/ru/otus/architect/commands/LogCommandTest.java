package ru.otus.architect.commands;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogCommandTest {

    private static final String ERROR_MSG = "error";

    @Mock
    Logger logger;

    @Test
    @DisplayName("Команда пишет в лог")
    void testExecute() {
        // arrange
        when(logger.getLevel()).thenReturn(Level.INFO);
        var cut = new LogCommand(new RuntimeException(ERROR_MSG), logger);
        // act
        cut.execute();
        // assert
        verify(logger, times(1)).log(Level.INFO, ERROR_MSG);
    }
}
