package com.boa.candidate;

import com.boa.common.PersonModel;
import com.boa.interview.InterviewEntity;
import com.boa.interview.InterviewNotFoundException;
import com.boa.interview.InterviewParticipantEntity;
import com.boa.interview.ParticipantNotFoundException;
import com.boa.user.UserEntity;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * This repository manages information about a candidate, including their interviews and capturing the people
 * participating in the interview.
 */
@Repository
public class CandidateRepositoryImpl implements CandidateRepository {
    private EntityManager em;
    
    @Autowired
    public CandidateRepositoryImpl(EntityManager em) {
        this.em = em;
    }
    
    /**
     * Retrieve a candidate entity from the database
     *
     * @param id    The unique candidate ID
     *
     * @return The candidate entity
     *
     * @throws CandidateNotFoundException if the candidate does not exist
     */
    @Override
    public CandidateEntity getCandidate(int id) throws CandidateNotFoundException {
        
        CandidateEntity entity = this.em.find(CandidateEntity.class, id);
        
        if (entity == null) {
            throw new CandidateNotFoundException(id);
        }
        
        return entity;
    }
    
    /**
     * Create a candidate in the system
     *
     * @param model The model describing information about the candidate
     *
     * @return  The candidate entity
     */
    @Override
    public CandidateEntity createCandidate(PersonModel model) {
        CandidateEntity entity = new CandidateEntity();
        
        entity.setFirstName(model.getFirstName());
        entity.setLastName(model.getLastName());
        entity.setEmail(model.getEmail());
        entity.setActive(true);
        
        this.em.persist(entity);
        
        return entity;
    }
    
    /**
     * This method fetches all candidates in the underlying database
     *
     * @return  The candidates
     * @param activeOnly
     */
    @Override
    public List<CandidateEntity> getCandidates(boolean activeOnly) {
        if (activeOnly) {
            return this.em.createQuery("SELECT c FROM Candidate c WHERE c.active = true", CandidateEntity.class).getResultList();
        }
        
        return this.em.createQuery("SELECT c FROM Candidate c", CandidateEntity.class).getResultList();
    }
    
    /**
     * This method creates an interview in the system
     *
     * @param candidateId       The candidate ID
     * @param scheduledTime     The time for the interview
     *
     * @return An interview entity
     *
     * @throws CandidateNotFoundException if the candidate does not exist
     */
    @Override
    public InterviewEntity createInterview(Integer candidateId, LocalDateTime scheduledTime) throws CandidateNotFoundException {
        Objects.requireNonNull(candidateId, "Candidate required");
        Objects.requireNonNull(scheduledTime, "Scheduled time required");
        
        CandidateEntity candidateEntity = getCandidate(candidateId);
        InterviewEntity interviewEntity = new InterviewEntity();
        
        interviewEntity.setCandidate(candidateEntity);
        interviewEntity.setScheduledTime(Timestamp.valueOf(scheduledTime));
        
        this.em.persist(interviewEntity);

        return interviewEntity;
    }
    
    /**
     * Remove an interview from the system
     *
     * @param candidateId       The candidate ID
     * @param interviewId       The interview ID
     *
     * @throws InterviewNotFoundException if the interview does not exist
     */
    @Override
    public void removeInterview(Integer candidateId, Integer interviewId) throws InterviewNotFoundException {
        InterviewEntity entity = getInterview(candidateId, interviewId);
        
        this.em.remove(entity);
        this.em.flush();
    }
    
    /**
     * This method retrives a specific interview entity
     *
     * @param candidateId   The candidate ID
     * @param interviewId   The interview ID
     *
     * @return The interview entity
     *
     * @throws InterviewNotFoundException if the interview does not exist
     */
    @Override
    public InterviewEntity getInterview(Integer candidateId, Integer interviewId) throws InterviewNotFoundException {
        Objects.requireNonNull(candidateId, "Candidate ID is required");
        Objects.requireNonNull(interviewId, "Interview ID is required");
        
        List<InterviewEntity> results = this.em.createQuery("SELECT i FROM Interview i WHERE i.id = :interviewId AND i.candidate.id = :candidateId", InterviewEntity.class).
                                           setParameter("interviewId", interviewId).
                                           setParameter("candidateId", candidateId).getResultList();
        
        if (results.isEmpty()) {
            throw new InterviewNotFoundException(interviewId);
        }
        
        return results.get(0);
    }
    
    /**
     * This method creates an interview participant entry in the system.  The participant entry maps a user of the
     * tracking system to a particular interview.  The entry also provides a way to capture feedback about the
     * actual interview.
     *
     * @param interviewEntity   The interview
     * @param userEntity        The user to made a participant
     *
     * @return The participant entity
     */
    @Override
    public InterviewParticipantEntity createParticipant(InterviewEntity interviewEntity, UserEntity userEntity) {
        Objects.requireNonNull(interviewEntity, "Interview required");
        Objects.requireNonNull(userEntity, "User required");
        
        InterviewParticipantEntity entity = new InterviewParticipantEntity();
        
        entity.setInterview(interviewEntity);
        entity.setParticipant(userEntity);

        List<InterviewParticipantEntity> participants = interviewEntity.getParticipants();
        
        if (participants == null) {
            participants = new ArrayList<>();
            interviewEntity.setParticipants(participants);
        }
        
        interviewEntity.getParticipants().add(entity);
    
        this.em.persist(entity);
        this.em.flush();
        
        return entity;
    }
    
    /**
     * Remove a participant from an interview
     *
     * @param interviewEntity   The interview entity
     * @param userEntity The entity representing the participant
     */
    @Override
    public void removeParticipant(InterviewEntity interviewEntity, UserEntity userEntity)
    throws ParticipantNotFoundException {
        InterviewParticipantEntity entity = getParticipant(interviewEntity,userEntity);
    
        interviewEntity.getParticipants().remove(entity);

        this.em.remove(entity);
        this.em.flush();
    }
    
    /**
     * Find the participation entity for a given interview/user
     *
     * @param interviewEntity   The interview
     * @param userEntity The user
     *
     * @return The participation entity
     *
     * @throws ParticipantNotFoundException If the participation was not found
     */
    @Override
    public InterviewParticipantEntity getParticipant(InterviewEntity interviewEntity, UserEntity userEntity)
    throws ParticipantNotFoundException {
        List<InterviewParticipantEntity> results = this.em.createQuery("SELECT p FROM InterviewParticipant p WHERE p.interview = :interview AND p.participant = :participant", InterviewParticipantEntity.class)
                                                   .setParameter("interview", interviewEntity)
                                                   .setParameter("participant", userEntity).getResultList();
    
        if (results.isEmpty()) {
            throw new ParticipantNotFoundException(interviewEntity.getId(), userEntity.getId());
        }
    
        return results.get(0);
    }
}
