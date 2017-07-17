package com.boa.interview;

import com.boa.user.UserEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This class provides the JPA mapping to the interview_participant table, a join table used to specify the users
 * that are participating in an interview of a candidate as well as to capture feedback from the interview.
 */
@Entity(name = "InterviewParticipant")
@Table(name = "interview_participant")
public class InterviewParticipantEntity {
    private Integer id;
    private InterviewEntity interview;
    private UserEntity participant;
    private boolean organizer;
    private String feedback;

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @ManyToOne
    public InterviewEntity getInterview() {
        return interview;
    }
    
    public void setInterview(InterviewEntity interview) {
        this.interview = interview;
    }
    
    @ManyToOne
    public UserEntity getParticipant() {
        return participant;
    }
    
    public void setParticipant(UserEntity participant) {
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
