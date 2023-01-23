package ru.otus.architect.commands;

public class RetryCommand implements Command {
    private final String name = "Commands.RetryCommand";
    private final Command command;

    public RetryCommand(Command command) {
        this.command = command;
    }

    @Override
    public void execute() {
        command.execute();
    }

    @Override
    public String getName() {
        return name;
    }
}
