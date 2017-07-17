package com.boa.interview;

import com.boa.common.PersonModel;

/**
 * This model describes an interview participant - a person that will be part of a candidate's interview and
 * ultimately capture feedback about the interview.  For each interview, there can be an organizer that is
 * primarily responsible for the scheduling and management of the interview itself.
 */
public class ParticipantModel {
    private PersonModel participant;
    private boolean organizer;
    private String feedback;
    
    public PersonModel getParticipant() {
        return participant;
    }
    
    public void setParticipant(PersonModel participant) {
        this.participant = participant;
    }
    
    public boolean isOrganizer() {
        return organizer;
    }
    
    public void setOrganizer(boolean organizer) {
        this.organizer = organizer;
    }
    
    public String getFeedback() {
        return feedback;
    }
    
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
