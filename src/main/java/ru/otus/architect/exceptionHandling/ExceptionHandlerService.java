package ru.otus.architect.exceptionHandling;

import ru.otus.architect.command.Command;
import ru.otus.architect.exceptionHandling.handlers.CommandExceptionHandler;

public interface ExceptionHandlerService {

    void handleException(Command command, Exception exception);

    void registerExceptionHandlingRule(
            Command command, Exception exception, CommandExceptionHandler exceptionHandler);

    void registerExceptionHandlingRule(Command command, CommandExceptionHandler exceptionHandler);

    void registerExceptionHandlingRule(Exception exception, CommandExceptionHandler exceptionHandler);
}
