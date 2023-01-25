package ru.otus.architect.commands.baseCommands;


import ru.otus.architect.commands.Command;
import ru.otus.architect.game.objects.characteristic.Movable;

public class MoveCommand implements Command {
    private Movable m;

    public MoveCommand(Movable m) {
        this.m = m;
    }

    @Override
    public void execute() {
        m.setPosition(m.getPosition().plus(m.getVelocity()));
    }
}
