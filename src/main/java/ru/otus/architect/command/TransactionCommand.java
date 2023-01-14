package ru.otus.architect.command;

import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class TransactionCommand implements Command {

    private Map<Command, Command> undoActionsMap;
    private Queue<Command> commandQueue;
    private Stack<Command> undoStack;

    public TransactionCommand(Map<Command, Command> undoActionsMap, Queue<Command> commandQueue) {
        this.undoActionsMap = undoActionsMap;
        this.commandQueue = commandQueue;
        undoStack = new Stack<>();
    }

    @Override
    public void execute() {
        try {
            while (!commandQueue.isEmpty()) {
                Command poll = commandQueue.poll();
                Command undoAction = undoActionsMap.get(poll);
                poll.execute();
                undoStack.add(undoAction);
            }
        } catch (Exception e) {
            undoStack.pop().execute();
            throw new RuntimeException(e);
        }

    }
}
