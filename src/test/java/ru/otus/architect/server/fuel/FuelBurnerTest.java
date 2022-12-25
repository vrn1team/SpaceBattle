package ru.otus.architect.server.fuel;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FuelBurnerTest {

    @Mock
    private IFuelConsumer burnable;
    public FuelBurner fuelBurner;
    private final int startFuel = 10;

    private final int consumption = 3;

    @BeforeEach
    void init() {
        when(burnable.getFuelLvl()).thenReturn(startFuel);
        when(burnable.getConsumption()).thenReturn(consumption);
        fuelBurner = new FuelBurner(burnable);
    }

    @Test
    @DisplayName("Положительный тест")
    void normalTest() {
        assertDoesNotThrow(() -> fuelBurner.burnFuel());
        //проверка, что значение топлива менялось один раз
        verify(burnable, times(1)).setFuelLvl(any());
        //проверка, что изменения были на нужное нам значение
        verify(burnable, times(1)).setFuelLvl(startFuel - consumption);
    }

    @Test
    @DisplayName("Случай, когда расходуем топливо \"в ноль\"")
    void zeroFuelTest() {
        int consumption = burnable.getConsumption();
        when(burnable.getFuelLvl()).thenReturn(consumption);

        assertDoesNotThrow(() -> fuelBurner.burnFuel());
    }

    @Test
    @DisplayName("Пытаемся израсходовать больше топлива, чем есть")
    void negativedFuelTest() {
        when(burnable.getConsumption()).thenReturn(1000);
        assertThrows(Exception.class, () -> fuelBurner.burnFuel());
        verify(burnable, times(0)).setFuelLvl(any());
    }
}