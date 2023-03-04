package ru.otus.architect.commands.macro;

import ru.otus.architect.commands.Command;
import ru.otus.architect.commands.stoppable.Stoppable;
import ru.otus.architect.game.objects.characteristic.Movable;

import java.util.Queue;

public class MoveStepByStepCommand implements Command, Stoppable {

    private final Movable movable;
    private final int steps;
    private final Queue<Command> commandQueue;
    private boolean stopped;

    public MoveStepByStepCommand(Movable movable, int numSteps, Queue<Command> commandQueue) {
        this.movable = movable;
        this.steps = numSteps;
        this.commandQueue = commandQueue;
    }

    @Override
    public void execute() {
        for (int i = 0; i < steps; i++)  {
            var command = new StoppableMoveCommand(movable);
            commandQueue.offer(command);
        }
    }

    @Override
    public void stop() {
        stopped = true;
    }
}
