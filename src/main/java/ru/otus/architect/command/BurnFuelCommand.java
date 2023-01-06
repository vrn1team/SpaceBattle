package ru.otus.architect.command;

import ru.otus.architect.command.exceptions.LowFuelException;
import ru.otus.architect.fuel.FuelConsumer;

public class BurnFuelCommand implements Command {

    private final FuelConsumer fuelConsumer;

    public BurnFuelCommand(FuelConsumer fuelConsumer) {
        this.fuelConsumer = fuelConsumer;
    }


    @Override
    public void execute() {
        int newFuelLvl = fuelConsumer.getFuelLevel() - fuelConsumer.getConsumption();
        if (newFuelLvl < 0) {
            throw new LowFuelException("Fuel level too low");
        }
        fuelConsumer.setFuelLevel(newFuelLvl);
    }
}
