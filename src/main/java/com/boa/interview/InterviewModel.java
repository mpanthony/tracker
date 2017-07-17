package com.boa.interview;

import com.boa.common.PersonModel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This model describes an interview
 */
public class InterviewModel {
    private Integer id;
    private PersonModel candidate;
    private LocalDateTime scheduledTime;
    private List<ParticipantModel> participants = new ArrayList<>();
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public PersonModel getCandidate() {
        return candidate;
    }
    
    public void setCandidate(PersonModel candidate) {
        this.candidate = candidate;
    }
    
    public LocalDateTime getScheduledTime() {
        return this.scheduledTime;
    }
    
    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
    
    public void addParticipant(ParticipantModel participant) {
        this.participants.add(participant);
    }
    
    public List<ParticipantModel> getParticipants() {
        return participants;
    }
    
    public void setParticipants(List<ParticipantModel> participants) {
        this.participants = participants;
    }
}
