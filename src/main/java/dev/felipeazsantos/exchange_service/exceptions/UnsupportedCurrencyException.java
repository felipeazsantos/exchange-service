package dev.felipeazsantos.exchange_service.exceptions;

public class UnsupportedCurrencyException extends RuntimeException{

    public UnsupportedCurrencyException(String message) {
        super(message);
    }
}
