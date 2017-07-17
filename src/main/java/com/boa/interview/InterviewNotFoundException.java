package com.boa.interview;

import com.boa.common.TrackerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InterviewNotFoundException extends TrackerException {
    public InterviewNotFoundException(Integer id) {
        super("Interview " + id + " not found");
    }
}
