package ru.otus.architect.commands;

import ch.qos.logback.classic.Logger;
import ru.otus.architect.exceptionHandling.ExceptionHandlerService;
import ru.otus.architect.exceptionHandling.handlers.CommandExceptionLogHandler;
import ru.otus.architect.exceptionHandling.handlers.CommandExceptionRetryHandler;
import ru.otus.architect.exceptionHandling.handlers.CommandExceptionSecondRetryHandler;
import ru.otus.architect.exceptions.FirstRetryCommandException;
import ru.otus.architect.exceptions.SecondRetryCommandException;

import java.util.Queue;

public class TwiceRetryStrategyRegistrationCommand implements Command {
    private final ExceptionHandlerService handlerService;
    private final Queue<Command> commandQueue;
    private final Logger logger;

    public TwiceRetryStrategyRegistrationCommand(ExceptionHandlerService handlerService, Queue<Command> commandQueue, Logger logger) {
        this.handlerService = handlerService;
        this.commandQueue = commandQueue;
        this.logger = logger;
    }

    @Override
    public void execute() {
        handlerService.registerHandlingRule(new CommandExceptionRetryHandler(commandQueue));
        handlerService.registerExceptionHandlingRule(FirstRetryCommandException.class, new CommandExceptionSecondRetryHandler(commandQueue));
        handlerService.registerExceptionHandlingRule(SecondRetryCommandException.class, new CommandExceptionLogHandler(logger, commandQueue));
    }
}
