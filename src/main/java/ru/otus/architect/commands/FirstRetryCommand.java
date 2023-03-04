package ru.otus.architect.commands;

import ru.otus.architect.exceptions.FirstRetryCommandException;

public class FirstRetryCommand implements Command {
    private final Command command;

    public FirstRetryCommand(Command command) {
        this.command = command;
    }

    @Override
    public void execute() {
        try {
            command.execute();
        } catch (Exception exception) {
            throw new FirstRetryCommandException(exception);
        }
    }

}
