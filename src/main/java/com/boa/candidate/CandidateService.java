package com.boa.candidate;

import com.boa.common.Document;
import com.boa.common.DocumentNotFoundException;
import com.boa.common.TrackerException;
import com.boa.interview.InterviewModel;
import com.boa.common.PersonModel;
import com.boa.interview.InterviewNotFoundException;
import com.boa.interview.ScheduledInterviewModel;
import java.util.List;

public interface CandidateService {
    
    CandidateModel getCandidate(int id) throws CandidateNotFoundException;
    
    CandidateModel createCandidate(PersonModel model);
    
    List<CandidateModel> getCandidates(boolean activeOnly);
    
    List<InterviewModel> getCandidateInterviews(Integer candidateId) throws CandidateNotFoundException;
    
    InterviewModel getCandidateInterview(Integer candidateId, Integer interviewId) throws InterviewNotFoundException;
    
    InterviewModel scheduleInterview(Integer candidateId, ScheduledInterviewModel scheduledInterviewModel)
    throws TrackerException;
    
    void cancelInterview(Integer candidateId, Integer interviewId) throws InterviewNotFoundException;
    
    void addInterviewParticipant(Integer candidateId, Integer interviewId, Integer participantId)
    throws TrackerException;
    
    public void removeInterviewParticipant(Integer candidateId, Integer interviewId, Integer participantId)
    throws TrackerException;
    
    void saveResume(Integer candidateID, Document document) throws CandidateNotFoundException;
    
    Document getResume(Integer candidateId) throws CandidateNotFoundException, DocumentNotFoundException;
    
    void deleteResume(Integer candidateId) throws CandidateNotFoundException, DocumentNotFoundException;
    
    void setInterviewFeedback(Integer candidateId, Integer interviewId, Integer participantId, String feedback)
    throws TrackerException;
    
    void setCandidateActive(Integer candidateId, boolean isActive) throws CandidateNotFoundException;
}
