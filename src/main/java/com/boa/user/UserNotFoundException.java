package com.boa.user;

import com.boa.common.TrackerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends TrackerException {
    public UserNotFoundException(Integer id) {
        super("User " + id + " not found");
    }
}
