package ru.otus.architect.stubs;

import ru.otus.architect.exceptions.WritePositionException;
import ru.otus.architect.game.objects.characteristic.Movable;
import ru.otus.architect.game.objects.dimension.Coordinates;

public class LimitedMovableStub implements Movable {

    private Coordinates position = new Coordinates(0, 0);
    private Coordinates velocity = new Coordinates(0, 0);


    private Coordinates limit = new Coordinates(1, 1);


    @Override
    public Coordinates getPosition() {
        return position;
    }

    @Override
    public void setPosition(Coordinates newPosition) {
        if (newPosition.getX() >= limit.getX() || newPosition.getY() >= limit.getY()) {
            throw new WritePositionException();
        }
        this.position = newPosition;
    }

    @Override
    public Coordinates getVelocity() {
        return velocity;
    }

    public void setVelocity(Coordinates velocity) {
        this.velocity = velocity;
    }


    public Coordinates getLimit() {
        return limit;
    }

    public void setLimit(Coordinates limit) {
        this.limit = limit;
    }
}
