package ru.otus.architect.commands;

import ru.otus.architect.game.objects.Mobile;

public class MoveCommand implements Command {

    private final Mobile mobile;

    public MoveCommand(Mobile mobile) {
        this.mobile = mobile;
    }

    @Override
    public void execute() {
        mobile.setPosition(mobile.getPosition().vectorAdd(mobile.getVelocity()));
    }
}
