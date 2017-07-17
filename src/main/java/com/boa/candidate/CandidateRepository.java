package com.boa.candidate;

import com.boa.common.PersonModel;
import com.boa.interview.InterviewEntity;
import com.boa.interview.InterviewNotFoundException;
import com.boa.interview.InterviewParticipantEntity;
import com.boa.interview.ParticipantNotFoundException;
import com.boa.user.UserEntity;
import java.time.LocalDateTime;
import java.util.List;

public interface CandidateRepository {
    CandidateEntity getCandidate(int id) throws CandidateNotFoundException;
    CandidateEntity createCandidate(PersonModel model);
    List<CandidateEntity> getCandidates(boolean activeOnly);
    
    InterviewEntity createInterview(Integer candidateId, LocalDateTime scheduledTime) throws CandidateNotFoundException;
    void removeInterview(Integer candidateId, Integer interviewId) throws InterviewNotFoundException;
    InterviewEntity getInterview(Integer candidateId, Integer interviewId) throws InterviewNotFoundException;
    
    InterviewParticipantEntity getParticipant(InterviewEntity interviewEntity, UserEntity userEntity)
    throws ParticipantNotFoundException;
    InterviewParticipantEntity createParticipant(InterviewEntity interviewEntity, UserEntity userEntity);
    void removeParticipant(InterviewEntity interviewEntity, UserEntity userEntity) throws ParticipantNotFoundException;
}
