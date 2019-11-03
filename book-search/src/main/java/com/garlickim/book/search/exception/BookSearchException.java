package com.garlickim.book.search.exception;

public class BookSearchException extends RuntimeException {

    public BookSearchException() {
        super();
    }

    public BookSearchException(String message) {
        super(message);
    }

    public BookSearchException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookSearchException(Throwable cause) {
        super(cause);
    }

    protected BookSearchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
