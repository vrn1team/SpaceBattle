package ru.otus.architect.command;

import ru.otus.architect.fuel.IFuelConsumer;

public class BurnFuelCommand implements Command {

    private final IFuelConsumer burnable;

    public BurnFuelCommand(IFuelConsumer burnable) {
        this.burnable = burnable;
    }


    @Override
    public void execute() {
        int newFuelLvl = burnable.getFuelLvl() - burnable.getConsumption();
        if (newFuelLvl < 0) {
            throw new RuntimeException("Fuel level too low");
        }
        burnable.setFuelLvl(newFuelLvl);
    }
}
