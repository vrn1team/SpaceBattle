package ru.otus.architect.commands;

public interface Command {
    void execute();

    default String getName() {
        return "Command";
    }
}
