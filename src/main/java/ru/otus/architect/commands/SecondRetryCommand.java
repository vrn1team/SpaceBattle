package ru.otus.architect.commands;

import ru.otus.architect.exceptions.SecondRetryCommandException;

public class SecondRetryCommand implements Command {
    private final Command command;

    public SecondRetryCommand(Command command) {
        this.command = command;
    }

    @Override
    public void execute() {
        try {
            command.execute();
        } catch (Exception exception) {
            throw new SecondRetryCommandException(exception);
        }
    }
}
