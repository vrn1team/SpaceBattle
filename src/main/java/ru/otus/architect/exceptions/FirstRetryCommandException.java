package ru.otus.architect.exceptions;

public class FirstRetryCommandException extends RuntimeException {
    public FirstRetryCommandException(Throwable cause) {
        super(cause);
    }
}
