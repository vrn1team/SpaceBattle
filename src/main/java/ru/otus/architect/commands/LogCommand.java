package ru.otus.architect.commands;


import ch.qos.logback.classic.Logger;

public class LogCommand implements Command {
    private final static String MESSAGE = "Command {} complete with Exception {}";
    private final Logger log;
    private final Command command;
    private final Exception exception;

    public LogCommand(Logger log, Command command, Exception exception) {
        this.log = log;
        this.command = command;
        this.exception = exception;
    }

    @Override
    public void execute() {
        log.info(MESSAGE, command, exception);
    }

}
