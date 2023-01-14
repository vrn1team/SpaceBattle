package ru.otus.architect.exceptionHandling.handlers;

import ru.otus.architect.command.Command;

public class ExceptionLoggingHandler implements CommandExceptionHandler{

    @Override
    public void handleException(Command command, Exception exception) {
        System.out.println(exception.getMessage());
    }
}
