package ru.otus.architect.exceptionHandling.handlers;

import ru.otus.architect.command.Command;

public interface CommandExceptionHandler {
    void handleException(Command command, Exception exception);
}
