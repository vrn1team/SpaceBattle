package ru.otus.architect.commands;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

public class LogCommand implements Command {
    private final String name = "Commands.LogCommand";
    private final Logger logger;
    private final Exception exception;

    public LogCommand(Exception exception, Logger logger) {
        this.exception = exception;
        this.logger = logger;
    }

    @Override
    public void execute() {
        logger.log(Level.ERROR, exception.getMessage());
    }

    @Override
    public String getName() {
        return name;
    }
}
