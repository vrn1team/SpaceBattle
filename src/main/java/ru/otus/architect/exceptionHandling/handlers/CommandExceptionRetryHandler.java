package ru.otus.architect.exceptionHandling.handlers;

import ru.otus.architect.commands.Command;
import ru.otus.architect.commands.FirstRetryCommand;

import java.util.Queue;

public class CommandExceptionRetryHandler implements CommandExceptionHandler {
    private final Queue<Command> commandQueue;

    public CommandExceptionRetryHandler(Queue<Command> commandQueue) {
        this.commandQueue = commandQueue;
    }

    @Override
    public void handleException(Command command, Exception exception) {
        if (!commandQueue.offer(new FirstRetryCommand(command))) {
            throw new RuntimeException("Can't offer command to queue");
        }
    }
}
