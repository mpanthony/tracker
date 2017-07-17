package com.boa.candidate;

import com.boa.common.Document;
import com.boa.common.DocumentNotFoundException;
import com.boa.common.ModelFactory;
import com.boa.common.TrackerException;
import com.boa.interview.InterviewEntity;
import com.boa.interview.InterviewModel;
import com.boa.common.PersonModel;
import com.boa.interview.InterviewNotFoundException;
import com.boa.interview.InterviewParticipantEntity;
import com.boa.interview.ScheduledInterviewModel;
import com.boa.user.UserEntity;
import com.boa.user.UserRepository;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class implements a service for managing candidates and their interviews
 */
@Component
public class CandidateServiceImpl implements CandidateService {
    private CandidateRepository candidateRepository;
    private UserRepository userRepository;
    private ModelFactory modelFactory;
    
    @Autowired
    public CandidateServiceImpl(CandidateRepository candidateRepository, UserRepository userRepository, ModelFactory modelFactory) {
        Objects.requireNonNull(candidateRepository, "Candidate repository required");
        Objects.requireNonNull(userRepository, "User repository required");
        Objects.requireNonNull(modelFactory, "Model factory required");
        
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;
        this.modelFactory = modelFactory;
    }
    
    /**
     * This method retrieves a model describing a given candidate
     *
     * @param id    The candidate ID
     *
     * @return      The candidate model
     *
     * @throws CandidateNotFoundException if the candidate does not exist
     */
    @Override
    public CandidateModel getCandidate(int id) throws CandidateNotFoundException {
        return modelFactory.toModel(this.candidateRepository.getCandidate(id));
    }
    
    /**
     * This method creates a candidate
     *
     * @param model The model used to describe the new candidate
     *
     * @return  The model describing the candidate
     */
    @Override
    public CandidateModel createCandidate(PersonModel model) {
        CandidateEntity entity = this.candidateRepository.createCandidate(model);
        
        return modelFactory.toModel(entity);
    }
    
    /**
     * This method sets the active state for a given candidate
     *
     * @param candidateId   The candidate ID
     * @param isActive      The active state
     *
     * @throws CandidateNotFoundException if the candidate is not found
     */
    @Override
    public void setCandidateActive(Integer candidateId, boolean isActive) throws CandidateNotFoundException {
        Objects.requireNonNull(candidateId, "Candidate ID required");
        
        CandidateEntity candidateEntity = this.candidateRepository.getCandidate(candidateId);
        
        candidateEntity.setActive(isActive);
    }
    
    /**
     * Retrieve models describing the candidates in the system
     *
     * @return  A list of candidate models
     * @param activeOnly
     */
    @Override
    public List<CandidateModel> getCandidates(boolean activeOnly) {
        return this.candidateRepository.getCandidates(activeOnly).stream()
               .map(candidate->this.modelFactory.toModel(candidate))
               .collect(Collectors.toList());
    }
    
    /**
     * Retrieve models describing all interviews scheduled for a particular candidate
     *
     * @param candidateId    The candidate ID
     *
     * @return A list of models describing all candidate interviews
     *
     * @throws CandidateNotFoundException if the candidate does not exist
     */
    @Override
    public List<InterviewModel> getCandidateInterviews(Integer candidateId)
    throws CandidateNotFoundException {
        CandidateEntity entity = this.candidateRepository.getCandidate(candidateId);
        
        // If there are no interviews return an empty list
        if (entity.getInterviews() == null) {
            return Collections.emptyList();
        }

        // Build a list, mapping each interview entity to an interview model.
        return entity.getInterviews().stream()
               .map(interview->this.modelFactory.toModel(interview))
                .collect(Collectors.toList());
    }
    
    /**
     * Retrieve a model for a specific interview of a candidate
     *
     * @param candidateId       The candidate ID
     * @param interviewId       The interview ID
     *
     * @return A model describing the interview
     *
     * @throws InterviewNotFoundException if the interview does not exist
     */
    @Override
    public InterviewModel getCandidateInterview(Integer candidateId, Integer interviewId) throws InterviewNotFoundException {
        return this.modelFactory.toModel(this.candidateRepository.getInterview(candidateId, interviewId));
    }
    
    /**
     * This method cancels a previously scheduled interview
     *
     * @param candidateId   The candidate ID
     * @param interviewId   The interview ID
     *
     * @throws InterviewNotFoundException if the interview does not exist
     */
    @Override
    public void cancelInterview(Integer candidateId, Integer interviewId) throws InterviewNotFoundException {
        this.candidateRepository.removeInterview(candidateId, interviewId);
    }
    
    /**
     * This method schedules an interview
     *
     * @param candidateId               The candidate ID
     * @param scheduledInterviewModel   The model describing the scheduled interview
     *
     * @return A model describing the scheduled interview
     *
     * @throws TrackerException if the interview cannot be scheduled
     */
    @Override
    public InterviewModel scheduleInterview(Integer candidateId, ScheduledInterviewModel scheduledInterviewModel)
    throws TrackerException {
        // Find the user that has organized the interview
        UserEntity userEntity = this.userRepository.getUser(scheduledInterviewModel.getOrganizerId());
        
        // Create the interview
        InterviewEntity interviewEntity = this.candidateRepository.createInterview(candidateId, scheduledInterviewModel.getScheduledTime());

        // Create the participation entity, which associates a user to the interview
        InterviewParticipantEntity participantEntity = this.candidateRepository.createParticipant(interviewEntity, userEntity);

        // Make this participant the organizer and record
        participantEntity.setOrganizer(true);
        
        // Return an interview model
        return this.modelFactory.toModel(interviewEntity);
    }
    
    /**
     * Add a participant to an existing interview
     *
     * @param candidateId   The candidate ID
     * @param interviewId   The interview ID
     * @param participantId The participant ID
     *
     * @throws TrackerException if the participant could not be added.
     */
    @Override
    public void addInterviewParticipant(Integer candidateId, Integer interviewId, Integer participantId)
    throws TrackerException {
        UserEntity userEntity = this.userRepository.getUser(participantId);
        InterviewEntity interviewEntity = this.candidateRepository.getInterview(candidateId, interviewId);
        
        // Create a new participant entry
        this.candidateRepository.createParticipant(interviewEntity, userEntity);
    }
    
    /**
     * Add a participant to an existing interview
     *
     * @param candidateId   The candidate ID
     * @param interviewId   The interview ID
     * @param participantId The participant ID
     *
     * @throws TrackerException if the participant could not be added.
     */
    @Override
    public void removeInterviewParticipant(Integer candidateId, Integer interviewId, Integer participantId)
    throws TrackerException {
        UserEntity userEntity = this.userRepository.getUser(participantId);
        InterviewEntity interviewEntity = this.candidateRepository.getInterview(candidateId, interviewId);
        
        // Create a new participant entry
        this.candidateRepository.removeParticipant(interviewEntity, userEntity);
    }
    
    @Override
    public void setInterviewFeedback(Integer candidateId, Integer interviewId, Integer participantId, String feedback)
    throws TrackerException {
        Objects.requireNonNull(candidateId, "Candidate ID is required");
        Objects.requireNonNull(interviewId, "Interview ID is required");
        
        InterviewEntity interviewEntity = this.candidateRepository.getInterview(candidateId, interviewId);
        UserEntity userEntity = this.userRepository.getUser(participantId);
        
        // Find the participation entity and capture the feedback
        InterviewParticipantEntity participantEntity = this.candidateRepository.getParticipant(interviewEntity, userEntity);
        
        participantEntity.setFeedback(feedback);
    }
    
    /**
     * Capture the resume content for the given candidate
     *
     * @param candidateID   The candidate ID
     * @param document The document to upload
     *
     * @throws CandidateNotFoundException if the candidate does not exist
     */
    @Override
    public void saveResume(Integer candidateID, Document document) throws CandidateNotFoundException {
        Objects.requireNonNull(candidateID, "Candidate ID required");
        Objects.requireNonNull(document, "Document required");
        
        CandidateEntity candidateEntity = candidateRepository.getCandidate(candidateID);
        
        candidateEntity.setResumeFilename(document.getName());
        candidateEntity.setResumeContent(document.getContent());
        candidateEntity.setResumeContentType(document.getContentType());
    }
    
    /**
     * Retrieve a resume for a particular candidate
     *
     * @param candidateID   The candidate ID
     *
     * @return  The resume Document
     *
     * @throws CandidateNotFoundException if the candidate was not found
     * @throws DocumentNotFoundException if a resume is not available
     */
    @Override
    public Document getResume(Integer candidateID) throws CandidateNotFoundException, DocumentNotFoundException {
        Objects.requireNonNull(candidateID, "Candidate ID required");
    
        // Find the candidate and ensure there is a resume
        CandidateEntity candidateEntity = candidateRepository.getCandidate(candidateID);
        
        if ((candidateEntity.getResumeFilename() == null) || candidateEntity.getResumeFilename().isEmpty()) {
            throw new DocumentNotFoundException("Resume not found");
        }
        
        // Build a Document object from the entity
        return new Document(candidateEntity.getResumeFilename(), candidateEntity.getResumeContent(), candidateEntity.getResumeContentType());
    }
    
    /**
     * Delete a previously stored resume
     *
     * @param candidateID   The candidate ID
     *
     * @throws CandidateNotFoundException If the candidate does not exist
     * @throws DocumentNotFoundException If the candidate does not have a resume
     */
    @Override
    public void deleteResume(Integer candidateID) throws CandidateNotFoundException, DocumentNotFoundException {
        Objects.requireNonNull(candidateID, "Candidate ID required");
    
        // Find the candidate and ensure there is a resume
        CandidateEntity candidateEntity = candidateRepository.getCandidate(candidateID);
    
        if ((candidateEntity.getResumeFilename() == null) || candidateEntity.getResumeFilename().isEmpty()) {
            throw new DocumentNotFoundException("Resume not found");
        }

        // Remove the resume content
        candidateEntity.setResumeFilename(null);
        candidateEntity.setResumeContentType(null);
        candidateEntity.setResumeContent(null);
    }
}
