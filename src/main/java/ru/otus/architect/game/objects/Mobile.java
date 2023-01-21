package ru.otus.architect.game.objects;

import ru.otus.architect.vector.Vector;

public interface Mobile {

    void setPosition(Vector position);

    Vector getPosition();

    Vector getVelocity();
}
