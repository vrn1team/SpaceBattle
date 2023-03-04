package ru.otus.architect.exceptionHandling.handlers;

import ch.qos.logback.classic.Logger;
import ru.otus.architect.commands.Command;
import ru.otus.architect.commands.LogCommand;

import java.util.Queue;

public class CommandExceptionLogHandler implements CommandExceptionHandler {
    private final Logger log;
    private final Queue<Command> commandQueue;

    public CommandExceptionLogHandler(Logger log, Queue<Command> commandQueue) {
        this.log = log;
        this.commandQueue = commandQueue;
    }

    @Override
    public void handleException(Command command, Exception exception) {
        if (!commandQueue.offer(new LogCommand(log, command, exception))) {
            throw new RuntimeException("Can't offer command to queue");
        }
    }

}
