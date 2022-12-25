package ru.otus.architect.server.fuel;

public interface IFuelConsumer {

    int getFuelLvl();

    int getConsumption();

    void setFuelLvl(Integer newFuelLvl);
}
