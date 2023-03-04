package ru.otus.architect.commands;

public interface ChangeableCommand extends Command {

    void changeCommand(Command command);
}
