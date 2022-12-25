package ru.otus.architect.server.fuel;

public class FuelBurner {

    private final IFuelConsumer burnable;

    public FuelBurner(IFuelConsumer burnable) {
        this.burnable = burnable;
    }

    public void burnFuel() {
        int newFuelLvl = burnable.getFuelLvl() - burnable.getConsumption();
        if (newFuelLvl < 0) {
            throw new RuntimeException("Fuel level too low");
        }
        burnable.setFuelLvl(newFuelLvl);
    }
}
