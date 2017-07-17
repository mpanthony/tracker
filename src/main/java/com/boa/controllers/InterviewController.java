package com.boa.controllers;

import com.boa.candidate.CandidateService;
import com.boa.common.TrackerException;
import com.boa.interview.InterviewModel;
import com.boa.interview.ScheduledInterviewModel;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller exposes endpoints for access candidate interviews
 */
@RestController
@RequestMapping(value = "/api/1.0")
public class InterviewController {
    private CandidateService candidateService;
    
    @Autowired
    public InterviewController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }
    
    /**
     * This method schedules an interview with a candidate
     *
     * @param candidateId   The candidate ID
     * @param scheduledInterviewModel   A model providing information about the interview being scheduled
     *
     * @return  A model describing the scheduled interview
     *
     * @throws TrackerException if the interview cannot be created
     */
    @Transactional
    @RequestMapping(value = "/candidates/{candidateId}/interviews", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody InterviewModel scheduleInterview(@PathVariable("candidateId") Integer candidateId, @RequestBody ScheduledInterviewModel scheduledInterviewModel) throws TrackerException {
        return this.candidateService.scheduleInterview(candidateId, scheduledInterviewModel);
    }
    
    /**
     * This method cancels a previously scheduled interview with a candidate
     *
     * @param candidateId   The candidate ID
     * @param interviewId   The interview ID
     *
     * @throws TrackerException If the interview could not cancelled
     */
    @Transactional
    @RequestMapping(value = "/candidates/{candidateId}/interviews/{interviewId}", produces = "application/json", method = RequestMethod.DELETE)
    public void cancelInterview(@PathVariable("candidateId") Integer candidateId, @PathVariable("interviewId") Integer interviewId) throws TrackerException {
        this.candidateService.cancelInterview(candidateId, interviewId);
    }
    
    /**
     * This method retrives a list of the interviews for a specific candidate
     *
     * @param id    The candidate ID
     *
     * @return  A list of models describing each interview
     *
     * @throws TrackerException if an error occured fetching the interviews
     */
    @RequestMapping(value = "/candidates/{candidateId}/interviews", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody List<InterviewModel> getCandidateInterviews(@PathVariable("candidateId") Integer id) throws TrackerException {
        // Fetch the candidate interviews
        return this.candidateService.getCandidateInterviews(id);
    }
    
    /**
     * This method fetches a specific interview for a candidate
     *
     * @param candidateId   The candidate ID
     * @param interviewId   The interview ID
     *
     * @return  A model describing the specific interview
     *
     * @throws TrackerException if the information could not be retrieved
     */
    @RequestMapping(value = "/candidates/{candidateId}/interviews/{interviewId}", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody InterviewModel getCandidateInterview(@PathVariable("candidateId") Integer candidateId, @PathVariable("interviewId") Integer interviewId) throws TrackerException {
        return this.candidateService.getCandidateInterview(candidateId, interviewId);
    }
    
    /**
     * This method adds a participant user to an interview
     *
     * @param candidateId   The candidate ID
     * @param interviewId   The interview ID
     * @param participantId The ID of the user being added as an inteview participant
     *
     * @throws TrackerException If the participant could not be added
     */
    @Transactional
    @RequestMapping(value = "/candidates/{candidateId}/interviews/{interviewId}/participant/{participantId}", produces = "application/json", method = RequestMethod.POST)
    public void addInterviewParticipant(   @PathVariable("candidateId") Integer candidateId,
                                           @PathVariable("interviewId") Integer interviewId,
                                           @PathVariable("participantId") Integer participantId) throws TrackerException {
        this.candidateService.addInterviewParticipant(candidateId, interviewId, participantId);
    }
    
    /**
     * This method removes a particular user as an interview participant
     *
     * @param candidateId   The candidate ID
     * @param interviewId   The interview ID
     * @param participantId The ID of the user being removed from the interview
     *
     * @throws TrackerException if the participant could not be removed
     */
    @Transactional
    @RequestMapping(value = "/candidates/{candidateId}/interviews/{interviewId}/participant/{participantId}", method = RequestMethod.DELETE)
    public void removeInterviewParticipant( @PathVariable("candidateId") Integer candidateId,
                                            @PathVariable("interviewId") Integer interviewId,
                                            @PathVariable("participantId") Integer participantId) throws TrackerException {
        this.candidateService.removeInterviewParticipant(candidateId, interviewId, participantId);
    }
    
    /**
     * This method allows feedback on a particular interview to be captured
     *
     * @param candidateId       The candidate ID
     * @param interviewId       The interview ID
     * @param participantId     The participant ID
     * @param feedback          The feedback
     *
     * @throws TrackerException if an error occurs
     */
    @Transactional
    @RequestMapping(value = "/candidates/{candidateId}/interviews/{interviewId}/participant/{participantId}/feedback", method = RequestMethod.PUT)
    public void addFeedback( @PathVariable("candidateId") Integer candidateId,
                             @PathVariable("interviewId") Integer interviewId,
                             @PathVariable("participantId") Integer participantId,
                             @RequestBody() String feedback) throws TrackerException {
        this.candidateService.setInterviewFeedback(candidateId, interviewId, participantId, feedback);
    }
}
