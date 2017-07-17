package com.boa.common;

import com.boa.candidate.CandidateEntity;
import com.boa.candidate.CandidateModel;
import com.boa.interview.InterviewEntity;
import com.boa.interview.InterviewModel;
import com.boa.interview.InterviewParticipantEntity;
import com.boa.interview.ParticipantModel;
import com.boa.user.UserEntity;
import com.boa.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This component is used to build models from entity objects
 */
@Component
public class ModelFactory {
    private UrlBuilder urlBuilder;

    @Autowired
    public ModelFactory(UrlBuilder builder) {
        this.urlBuilder = builder;
    }
    
    /**
     * Build a user model from an entity
     *
     * @param entity    The user entity
     *
     * @return  A user model
     */
    public UserModel toModel(UserEntity entity) {
        UserModel model = new UserModel();
        
        model.setId(entity.getId());
        model.setFirstName(entity.getFirstName());
        model.setLastName(entity.getLastName());
        model.setEmail(entity.getEmail());
        
        return model;
    }
    
    /**
     * Build a candidate model from a candidate entity
     *
     * @param entity    The candidate entity
     *
     * @return The candidate model
     */
    public CandidateModel toModel(CandidateEntity entity) {
        CandidateModel model = new CandidateModel();
        
        model.setId(entity.getId());
        model.setFirstName(entity.getFirstName());
        model.setLastName(entity.getLastName());
        model.setEmail(entity.getEmail());
        model.setActive(entity.isActive());
    
        // If there is a resume file name captured, build the URL to fetch the resume itself
        if ((entity.getResumeFilename() != null) && (entity.getResumeFilename().length() > 0)) {
            model.setResumeUrl(this.urlBuilder.buildResumeUrl(entity.getId()));
        }
        
        return model;
    }
    
    /**
     * Build a participant model from a participant entity
     *
     * @param entity    The participant entity
     *
     * @return The participant model
     */
    public ParticipantModel toModel(InterviewParticipantEntity entity) {
        ParticipantModel model = new ParticipantModel();
        
        model.setOrganizer(entity.isOrganizer());
        model.setParticipant(toModel(entity.getParticipant()));
        model.setFeedback(entity.getFeedback());
        
        return model;
    }
    
    /**
     * Build an interview model from an interview entity
     *
     * @param entity    The interview entity
     *
     * @return The interview model
     */
    public InterviewModel toModel(InterviewEntity entity) {
        InterviewModel model = new InterviewModel();
        
        model.setId(entity.getId());
        model.setCandidate(toModel(entity.getCandidate()));
        model.setScheduledTime(entity.getScheduledTime().toLocalDateTime());
        
        if (entity.getParticipants() != null) {
            entity.getParticipants().stream()
            .map(this::toModel)
            .forEach(model::addParticipant);
        }
        
        return model;
    }
}
