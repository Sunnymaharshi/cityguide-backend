package com.cityguide.backend.exceptions;

public class UnAuthorisedException extends RuntimeException{
    public UnAuthorisedException() {
        super();
    }

    public UnAuthorisedException(String message) {
        super(message);
    }

    public UnAuthorisedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAuthorisedException(Throwable cause) {
        super(cause);
    }

    protected UnAuthorisedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
