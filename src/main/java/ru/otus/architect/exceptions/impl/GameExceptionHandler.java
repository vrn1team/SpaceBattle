package ru.otus.architect.exceptions.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.architect.commands.Command;
import ru.otus.architect.commands.LogCommand;
import ru.otus.architect.commands.RetryCommand;
import ru.otus.architect.exceptions.ExceptionHandler;

import java.util.Map;
import java.util.Optional;
import java.util.Queue;

public class GameExceptionHandler implements ExceptionHandler {

    private final Map<String, Map<Class<? extends Exception>, Class<? extends Command>>> hooks;
    private final Queue<Command> commands;

    public GameExceptionHandler(Map<String, Map<Class<? extends Exception>, Class<? extends Command>>> hooks,
                                Queue<Command> commands) {
        this.hooks = hooks;
        this.commands = commands;
    }

    @Override
    public void handle(Command cmd, Exception ex) {
        Optional.ofNullable(hooks.get(cmd.getName()))
                .map(exceptionHooks -> exceptionHooks.get(ex.getClass()))
                .map(hookCmd -> createHandlerCommand(hookCmd, cmd, ex))
                .ifPresent(commands::add);
    }

    private Command createHandlerCommand(Class<? extends Command> hookCmdClass, Command cmd, Exception ex) {
        if (hookCmdClass.equals(LogCommand.class)) {
            return new LogCommand(ex, createLogger(cmd.getClass()));
        } else if (hookCmdClass.equals(RetryCommand.class)) {
            return new RetryCommand(cmd);
        } else {
            return new LogCommand(new IllegalArgumentException("wrong helper command"), createLogger(LogCommand.class));
        }
    }

    private Logger createLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz.getName());
    }
}
