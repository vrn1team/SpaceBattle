package ru.otus.architect.commands.macro;

import ru.otus.architect.commands.Command;
import ru.otus.architect.exceptions.handlers.MacroCommandExceptionHandler;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;

public class TransactionalMacroCommand implements Command {
    private final Queue<Command> commands;
    private final MacroCommandExceptionHandler exceptionHandler;
    private final Map<Undoable, Consumer<? super Undoable>> initialStates;

    public TransactionalMacroCommand(List<Command> commands,
                                     Map<Undoable, Consumer<? super Undoable>> initialStates) {
        this.commands = new ArrayDeque<>(commands);
        this.initialStates = initialStates;
        exceptionHandler = new MacroCommandExceptionHandler(this.commands);
    }

    @Override
    public void execute() {
        while (!commands.isEmpty()) {
            var cmd = commands.poll();
            try {
                cmd.execute();
            } catch (Exception ex) {
                rollback();
                exceptionHandler.handle(cmd, ex);
            }
        }
    }

    private void rollback() {
        initialStates.forEach((object, rollbackFun) -> rollbackFun.accept(object));
    }
}
