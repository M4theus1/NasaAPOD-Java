package br.com.unifacol.nasaapod.exception;

public class InvalidDateException extends RuntimeException {

    public InvalidDateException(String message) {
        super(message);
    }
}
