package ru.otus.architect.game;

import ru.otus.architect.commands.Command;
import ru.otus.architect.exceptions.ExceptionHandler;

import java.util.Queue;

public class Game {

    private final Queue<Command> commandQueue;
    private final ExceptionHandler exceptionHandler;

    public Game(Queue<Command> commandQueue, ExceptionHandler exceptionHandler) {
        this.commandQueue = commandQueue;
        this.exceptionHandler = exceptionHandler;
    }

    public void run() {

        while (!commandQueue.isEmpty()) {
            var cmd = commandQueue.poll();

            try {
                cmd.execute();
            } catch (Exception ex) {
                exceptionHandler.handle(cmd, ex);
            }
        }
    }
}
