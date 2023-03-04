package ru.otus.architect.exceptionHandling.handlers;

import ru.otus.architect.commands.Command;
import ru.otus.architect.commands.SecondRetryCommand;

import java.util.Queue;

public class CommandExceptionSecondRetryHandler implements CommandExceptionHandler {
    private final Queue<Command> commandQueue;

    public CommandExceptionSecondRetryHandler(Queue<Command> commandQueue) {
        this.commandQueue = commandQueue;
    }

    @Override
    public void handleException(Command command, Exception exception) {
        if (!commandQueue.offer(new SecondRetryCommand(command))) {
            throw new RuntimeException("Can't offer command to queue");
        }
    }

}
