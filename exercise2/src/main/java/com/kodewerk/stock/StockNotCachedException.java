package com.kodewerk.stock;

public class StockNotCachedException extends Exception {
    public StockNotCachedException() {
        super();
    }

    public StockNotCachedException(String message) {
        super(message);
    }

    public StockNotCachedException(String message, Throwable cause) {
        super(message, cause);
    }

    public StockNotCachedException(Throwable cause) {
        super(cause);
    }
}
