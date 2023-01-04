package ru.otus.architect.commands;

import ru.otus.architect.transforms.Movable;

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
