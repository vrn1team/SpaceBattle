package ru.otus.architect.transforms;

import ru.otus.architect.base.Coordinates;

public interface Movable {
    Coordinates getPosition();

    void setPosition(Coordinates newPosition);

    Coordinates getVelocity();
}
