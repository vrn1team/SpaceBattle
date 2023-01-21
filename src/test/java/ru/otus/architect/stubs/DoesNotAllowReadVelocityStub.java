package ru.otus.architect.stubs;

import ru.otus.architect.game.objects.dimension.Coordinates;
import ru.otus.architect.exceptions.ReadVelocityException;
import ru.otus.architect.game.objects.characteristic.Movable;

public class DoesNotAllowReadVelocityStub implements Movable {

    private Coordinates position = new Coordinates(0, 0);
    private Coordinates velocity;

    @Override
    public Coordinates getPosition() {
        return position;
    }

    @Override
    public void setPosition(Coordinates newPosition) {
        this.position = position;
    }

    @Override
    public Coordinates getVelocity() {
        throw new ReadVelocityException();
    }

    public void setVelocity(Coordinates velocity) {
        this.velocity = velocity;
    }
}
