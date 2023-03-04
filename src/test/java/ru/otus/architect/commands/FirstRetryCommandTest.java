package ru.otus.architect.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.architect.exceptions.FirstRetryCommandException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FirstRetryCommandTest {
    @Mock
    private Command command;

    private Command retryCommand;

    @BeforeEach
    void setUp() {
        retryCommand = new FirstRetryCommand(command);
    }

    @Test
    @DisplayName("Проверяем, что команда повторяется")
    void testExecute() {
        retryCommand.execute();
        verify(command, times(1)).execute();
    }

    @Test
    @DisplayName("Проверяем, что исключения будут обернуты в OneRetryCommandException")
    void testExceptionExecute() {
        doThrow(RuntimeException.class).when(command).execute();
        assertThrows(FirstRetryCommandException.class, () -> retryCommand.execute());
    }
}