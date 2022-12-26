package ru.otus.architect.game.objects;

import ru.otus.architect.vector.Vector;

public interface Accelerator {

    Vector getVelocity();

    void setVelocity(Vector velocity);

    double getAcceleration();
}
