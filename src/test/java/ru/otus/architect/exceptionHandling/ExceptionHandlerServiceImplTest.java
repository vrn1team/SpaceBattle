package ru.otus.architect.exceptionHandling;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.architect.command.Command;
import ru.otus.architect.command.exceptions.CheckFuelException;
import ru.otus.architect.command.exceptions.LowFuelException;
import ru.otus.architect.exceptionHandling.handlers.CommandExceptionHandler;
import ru.otus.architect.stubs.CommandStub;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlerServiceImplTest {

    private Command command;
    private ExceptionHandlerService exceptionHandler;
    @Mock
    private CommandExceptionHandler specificCommandSpecificExceptionHandler;
    @Mock
    private CommandExceptionHandler specificCommandHandler;
    @Mock
    private CommandExceptionHandler specificExceptionHandler;

    @BeforeEach
    void setUp() {
        command = new CommandStub();
        exceptionHandler = new ExceptionHandlerServiceImpl();
        //на LowFuelException ставим хэндлер 1
        exceptionHandler.registerExceptionHandlingRule(command, new LowFuelException("random text"),
                specificCommandSpecificExceptionHandler);
        // на все остальные ошибки - второй хэндлер
        exceptionHandler.registerExceptionHandlingRule(command, specificCommandHandler);

        exceptionHandler.registerExceptionHandlingRule(new CheckFuelException("random text2"),
                specificExceptionHandler);
    }

    @Test
    @DisplayName("Проверка на обработку конкретного исключения с конкретной командой значения")
    void normalTest() {
        assertDoesNotThrow(
                () -> exceptionHandler.handleException(command, new LowFuelException("This is exception text")));
        verify(specificCommandSpecificExceptionHandler, times(1)).handleException(any(), any());
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    @DisplayName("Проверка на обработку общих исключений для конкретной команды, у которой указан хэндлер для этого")
    void exceptionForCommandTest(Exception exception) {
        assertDoesNotThrow(() -> exceptionHandler.handleException(command, exception));
        verify(specificCommandHandler, times(1)).handleException(any(), any());
    }

    @Test
    @DisplayName("Проверка на обработку исключения, которое не привязано к команде при вызове командой, которой нет в обработчике")
    void generalExceptionTestWithoutExistingCommand() {
        assertDoesNotThrow(() -> exceptionHandler.handleException(() -> {
                },
                new CheckFuelException("This is exception text")));
        verify(specificExceptionHandler, times(1)).handleException(any(), any());
    }

    @ParameterizedTest
    @MethodSource("provideExceptions")
    void mustThrowException(Exception exception) {
        assertThrows(NoCorrectHandlerException.class, () -> exceptionHandler.handleException(
                () -> {
                },
                exception));
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of(new Exception()),
                Arguments.of(new RuntimeException("this is text")),
                Arguments.of(new CheckFuelException("qwe"))
        );
    }

    private static Stream<Arguments> provideExceptions() {
        return Stream.of(
                Arguments.of(new Exception()),
                Arguments.of(new RuntimeException("this is text")),
                Arguments.of(new IOException("random exception"))
        );
    }
}