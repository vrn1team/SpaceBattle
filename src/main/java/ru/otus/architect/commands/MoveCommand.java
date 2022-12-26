package ru.otus.architect.commands;

import ru.otus.architect.game.objects.Moveable;

public class MoveCommand implements Command{

    private final Moveable moveable;

    public MoveCommand(Moveable moveable) {
        this.moveable = moveable;
    }

    @Override
    public void execute() {
        moveable.setPosition(moveable.getPosition().vectorAdd(moveable.getVelocity()));
    }
}
