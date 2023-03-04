package ru.otus.architect.commands;

public class StoppableCommandImpl implements StoppableCommand{

    private final ChangeableCommand command;
    private final Command terminationCommand;

    public StoppableCommandImpl(Command command, Command terminationCommand) {
        this.command = new ChangeableCommandImpl(command);
        this.terminationCommand = terminationCommand;
    }

    @Override
    public void execute() {
        command.execute();
    }

    @Override
    public void stop() {
        command.changeCommand(terminationCommand);
    }
}
