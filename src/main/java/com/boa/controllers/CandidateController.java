package com.boa.controllers;

import com.boa.candidate.CandidateModel;
import com.boa.candidate.CandidateNotFoundException;
import com.boa.candidate.CandidateService;
import com.boa.common.PersonModel;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller exposes endpoints for accessing information about interview candidates
 */
@RestController
@RequestMapping(value = "/api/1.0")
public class CandidateController {
    private CandidateService candidateService;
    
    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }
    
    /**
     * Create a new candidate in the system
     *
     * @param model The model describing the candidate to create
     *
     * @return A model describing the created candidate
     */
    @Transactional
    @RequestMapping(value = "/candidates", method = RequestMethod.POST)
    public CandidateModel createCandidate(@RequestBody PersonModel model) {
        return this.candidateService.createCandidate(model);
    }
    
    /**
     * Set the active state of a candidate
     *
     * @param candidateId   The candidate ID
     * @param isActive      A flag indicating the active state
     *
     * @throws CandidateNotFoundException if the candidate was not found
     */
    @Transactional
    @RequestMapping(value = "/candidates/{candidateId}/active", method = RequestMethod.PUT)
    public void setCandidateActiveState(@PathVariable("candidateId") Integer candidateId, @RequestParam(value = "value", required = true) Boolean isActive)
    throws CandidateNotFoundException {
        this.candidateService.setCandidateActive(candidateId, isActive);
    }
    
    /**
     * Fetch all candidates in the system
     *
     * @return  A list of models describing each candidate
     */
    @RequestMapping(value = "/candidates", method = RequestMethod.GET)
    public List<CandidateModel> getCandidates(@RequestParam(value = "activeOnly", defaultValue = "true") Boolean activeOnly) {
        return this.candidateService.getCandidates(activeOnly);
    }
    
    /**
     * This method fetches information about a specific candidate
     *
     * @param id    The candidate ID
     *
     * @return  A model describing the candidate
     *
     * @throws CandidateNotFoundException if the candidate does not exist
     */
    @RequestMapping(value = "/candidates/{candidateId}", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody CandidateModel getCandidate(@PathVariable("candidateId") Integer id)
    throws CandidateNotFoundException {
        return this.candidateService.getCandidate(id);
    }
}
