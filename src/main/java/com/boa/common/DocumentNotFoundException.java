package com.boa.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DocumentNotFoundException extends TrackerException {
    public DocumentNotFoundException(String message) {
        super(message);
    }
}
