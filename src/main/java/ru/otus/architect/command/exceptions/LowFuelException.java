package ru.otus.architect.command.exceptions;

public class LowFuelException extends RuntimeException{

    public LowFuelException(String message) {
        super(message);
    }
}
