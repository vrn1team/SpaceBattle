package ru.otus.architect.commands;

import java.util.logging.Logger;

public class LogCommand implements Command {
    private final Logger logger;
    private final Exception exception;

    public LogCommand(Exception exception, Logger logger) {
        this.exception = exception;
        this.logger = logger;
    }

    @Override
    public void execute() {
        logger.log(logger.getLevel(), exception.getMessage());
    }
}
