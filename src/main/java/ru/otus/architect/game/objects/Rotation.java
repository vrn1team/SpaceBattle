package ru.otus.architect.game.objects;

import ru.otus.architect.angle.Angle;

public interface Rotation {

    Angle getAngle();

    void setAngle(Angle angle);

    Angle getAngularVelocity();
}
