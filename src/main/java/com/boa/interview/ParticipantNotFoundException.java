package com.boa.interview;

import com.boa.common.TrackerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ParticipantNotFoundException extends TrackerException {
    public ParticipantNotFoundException(Integer interviewId, Integer participantId) {
        super("Participant " + participantId + " not found in interview " + interviewId);
    }
}
