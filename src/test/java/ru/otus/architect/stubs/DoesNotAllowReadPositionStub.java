package ru.otus.architect.stubs;

import ru.otus.architect.base.Coordinates;
import ru.otus.architect.exceptions.ReadPositionException;
import ru.otus.architect.transforms.Movable;

public class DoesNotAllowReadPositionStub implements Movable {

    private Coordinates position = new Coordinates(0, 0);
    private Coordinates velocity;

    @Override
    public Coordinates getPosition() {
        throw new ReadPositionException();
    }

    @Override
    public void setPosition(Coordinates newPosition) {
        this.position = position;
    }

    @Override
    public Coordinates getVelocity() {
        return velocity;
    }

    public void setVelocity(Coordinates velocity) {
        this.velocity = velocity;
    }
}