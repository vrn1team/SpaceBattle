package ru.otus.architect.stubs;

import ru.otus.architect.fuel.FuelConsumer;

public class FuelConsumerStub implements FuelConsumer {
    private int fuelLevel = 10;

    @Override
    public int getFuelLevel() {
        return fuelLevel;
    }

    @Override
    public int getConsumption() {
        return 5;
    }

    @Override
    public void setFuelLevel(Integer newFuelLvl) {
        this.fuelLevel = newFuelLvl;
    }
}
