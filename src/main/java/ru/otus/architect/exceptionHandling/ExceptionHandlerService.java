package ru.otus.architect.exceptionHandling;

import ru.otus.architect.commands.Command;
import ru.otus.architect.exceptionHandling.handlers.CommandExceptionHandler;

public interface ExceptionHandlerService {

    void getHandleException(Class<? extends Command> commandClazz, Class<? extends Exception> exceptionClazz);

    void registerHandlingRule(Class<? extends Command> commandClazz, Class<? extends Exception> exceptionClazz, CommandExceptionHandler exceptionHandler);

    void registerCommandHandlingRule(Class<? extends Command> commandClazz, CommandExceptionHandler exceptionHandler);

    void registerExceptionHandlingRule(Class<? extends Exception> exceptionClazz, CommandExceptionHandler exceptionHandler);

}