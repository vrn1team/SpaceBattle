package ru.otus.architect.exceptionHandling.handlers;

import ru.otus.architect.commands.Command;

public interface CommandExceptionHandler {
    void handleException(Command command, Exception exception);
}