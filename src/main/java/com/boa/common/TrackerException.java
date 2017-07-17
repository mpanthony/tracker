package com.boa.common;

public class TrackerException extends Exception {
    public TrackerException(String message, Exception cause) {
        super(message, cause);
    }
    
    public TrackerException(String message) {
        super(message);
    }
}
