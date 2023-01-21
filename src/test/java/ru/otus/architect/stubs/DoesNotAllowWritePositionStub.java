package ru.otus.architect.stubs;

import ru.otus.architect.game.objects.dimension.Coordinates;
import ru.otus.architect.exceptions.WritePositionException;
import ru.otus.architect.game.objects.characteristic.Movable;

public class DoesNotAllowWritePositionStub implements Movable {

    private Coordinates position = new Coordinates(0, 0);
    private Coordinates velocity;

    @Override
    public Coordinates getPosition() {
        return position;
    }

    @Override
    public void setPosition(Coordinates newPosition) {
        throw new WritePositionException();
    }

    @Override
    public Coordinates getVelocity() {
        return velocity;
    }

    public void setVelocity(Coordinates velocity) {
        this.velocity = velocity;
    }
}
