package ru.otus.architect.commands;

import ru.otus.architect.context.ApplicationContext;
import ru.otus.architect.exceptionHandling.handlers.CommandExceptionLogHandler;
import ru.otus.architect.exceptionHandling.handlers.CommandExceptionRetryHandler;
import ru.otus.architect.exceptionHandling.handlers.CommandExceptionSecondRetryHandler;
import ru.otus.architect.exceptions.FirstRetryCommandException;
import ru.otus.architect.exceptions.SecondRetryCommandException;

public class TwiceRetryStrategyRegistrationCommand implements Command {
    private final Class<? extends Command> commandClazz;
    private final Class<? extends Exception> exceptionClazz;
    private final ApplicationContext context;

    public TwiceRetryStrategyRegistrationCommand(Class<? extends Command> commandClazz, Class<? extends Exception> exceptionClazz, ApplicationContext context) {
        this.commandClazz = commandClazz;
        this.exceptionClazz = exceptionClazz;
        this.context = context;
    }

    @Override
    public void execute() {
        context.getExceptionHandlerService()
                .registerHandlingRule(
                        commandClazz,
                        exceptionClazz,
                        new CommandExceptionRetryHandler(context.getCommandQueue()));
        context.getExceptionHandlerService()
                .registerExceptionHandlingRule(
                        FirstRetryCommandException.class,
                        new CommandExceptionSecondRetryHandler(context.getCommandQueue()));
        context.getExceptionHandlerService()
                .registerExceptionHandlingRule(
                        SecondRetryCommandException.class,
                        new CommandExceptionLogHandler(context.getLogger(), context.getCommandQueue()));
    }
}
