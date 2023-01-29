package ru.otus.architect.stubs;

import ru.otus.architect.commands.macro.Undoable;
import ru.otus.architect.game.objects.characteristic.Movable;
import ru.otus.architect.game.objects.dimension.Coordinates;

public class UndoableMovableStub implements Movable, Undoable {

    private Coordinates position = new Coordinates(0, 0);
    private Coordinates velocity = new Coordinates(0, 0);


    @Override
    public Coordinates getPosition() {
        return new Coordinates(position.getX(), position.getY());
    }

    @Override
    public void setPosition(Coordinates newPosition) {
        this.position = new Coordinates(newPosition.getX(), newPosition.getY());
    }

    @Override
    public Coordinates getVelocity() {
        return new Coordinates(velocity.getX(), velocity.getY());
    }

    public void setVelocity(Coordinates velocity) {
        this.velocity = velocity;
    }
}
