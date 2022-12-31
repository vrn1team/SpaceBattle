package ru.otus.architect.game.objects;

import ru.otus.architect.angle.Angle;

public interface AngularAccelerator {

    Angle getAngularVelocity();

    void setAngularVelocity(Angle angle);

    Angle getAngularAcceleration();
}
