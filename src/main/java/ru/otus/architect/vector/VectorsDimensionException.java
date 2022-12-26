package ru.otus.architect.vector;

public class VectorsDimensionException extends RuntimeException{

    public VectorsDimensionException() {
    }

    public VectorsDimensionException(String message) {
        super(message);
    }

    public VectorsDimensionException(String message, Throwable cause) {
        super(message, cause);
    }
}
