package ru.otus.architect.exceptionHandling;

public class NoCorrectHandlerException extends RuntimeException{
    public NoCorrectHandlerException(String message) {
        super(message);
    }
}
