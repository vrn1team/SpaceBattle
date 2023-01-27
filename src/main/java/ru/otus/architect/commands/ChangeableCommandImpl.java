package ru.otus.architect.commands;

public class ChangeableCommandImpl implements ChangeableCommand {

    private Command command;

    public ChangeableCommandImpl(Command command) {
        this.command = command;
    }

    @Override
    public void changeCommand(Command command) {
        this.command = command;
    }

    @Override
    public void execute() {
        command.execute();
    }
}
