package ru.otus.architect.commands;

import ru.otus.architect.game.objects.Boostable;

public class BoostCommand implements Command {

    private final Boostable boostable;

    public BoostCommand(Boostable boostable) {
        this.boostable = boostable;
    }

    @Override
    public void execute() {
        boostable.setVelocity(boostable.getVelocity().scalarAdd(boostable.getAcceleration()));
    }
}
