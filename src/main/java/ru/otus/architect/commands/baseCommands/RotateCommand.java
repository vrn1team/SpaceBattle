package ru.otus.architect.commands.baseCommands;

import ru.otus.architect.commands.Command;
import ru.otus.architect.game.objects.characteristic.Rotation;

public class RotateCommand implements Command {

    private final Rotation rotation;

    public RotateCommand(Rotation rotation) {
        this.rotation = rotation;
    }

    @Override
    public void execute() {
        try {
            rotation.setAngle(rotation.getAngle().add(rotation.getAngularVelocity()));
        } catch (Exception exception) {
            throw new RotateCommandException(exception);
        }
    }
}
