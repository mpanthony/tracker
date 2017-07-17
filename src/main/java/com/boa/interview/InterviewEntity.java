package com.boa.interview;

import com.boa.candidate.CandidateEntity;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This is the JPA entity mapped to the "interview" table
 */
@Entity(name = "Interview")
@Table(name = "interview")
public class InterviewEntity {
    private Integer id;
    private CandidateEntity candidate;
    private Timestamp scheduledTime;
    private List<InterviewParticipantEntity> participants;
    
    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @ManyToOne(targetEntity = CandidateEntity.class)
    public CandidateEntity getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateEntity candidate) {
        this.candidate = candidate;
    }
    
    public Timestamp getScheduledTime() {
        return scheduledTime;
    }
    
    public void setScheduledTime(Timestamp scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
    
    @OneToMany(targetEntity = InterviewParticipantEntity.class, mappedBy = "interview")
    public List<InterviewParticipantEntity> getParticipants() {
        return participants;
    }
    
    public void setParticipants(List<InterviewParticipantEntity> participants) {
        this.participants = participants;
    }
}
