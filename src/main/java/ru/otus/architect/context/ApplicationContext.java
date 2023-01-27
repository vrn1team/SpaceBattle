package ru.otus.architect.context;

import ch.qos.logback.classic.Logger;
import ru.otus.architect.commands.Command;
import ru.otus.architect.exceptionHandling.ExceptionHandlerService;

import java.util.Queue;

public interface ApplicationContext {

    ExceptionHandlerService getExceptionHandlerService();

    Queue<Command> getCommandQueue();

    Logger getLogger();
}
