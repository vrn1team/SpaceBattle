package ru.otus.architect.exceptions;

public class SecondRetryCommandException extends RuntimeException {
    public SecondRetryCommandException(Throwable cause) {
        super(cause);
    }
}
