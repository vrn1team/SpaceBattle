package ru.otus.architect.game.objects.characteristic;

import ru.otus.architect.game.objects.dimension.vector.Vector;

public interface Mobile {

    void setPosition(Vector position);

    Vector getPosition();

    Vector getVelocity();
}
