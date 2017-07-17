package com.boa.candidate;

import com.boa.common.TrackerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CandidateNotFoundException extends TrackerException {
    public CandidateNotFoundException(Integer id) {
        super("Candidate " + id + " not found");
    }
}
