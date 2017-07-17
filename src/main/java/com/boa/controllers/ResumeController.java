package com.boa.controllers;

import com.boa.candidate.CandidateNotFoundException;
import com.boa.candidate.CandidateService;
import com.boa.common.Document;
import com.boa.common.DocumentNotFoundException;
import java.io.IOException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * This controller class exposes endpoints to access a candidate's resume
 */
@RestController
@RequestMapping(value = "/api/1.0")
public class ResumeController {
    private CandidateService candidateService;
    
    @Autowired
    public ResumeController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }
    
    /**
     * Allow a client to upload a resume for a specific candidate.  Any existing resume is replaced.
     *
     * @param candidateId   The candidate ID
     * @param uploadFile The file to be uploaded
     *
     * @throws IOException  If the uploaded content cannot be read
     * @throws CandidateNotFoundException If the candidate does not exist
     */
    @Transactional
    @RequestMapping(path = "/candidate/{id}/resume", method = RequestMethod.POST)
    public void uploadResume(@PathVariable("id") Integer candidateId, @RequestParam("file") MultipartFile uploadFile)
    throws IOException, CandidateNotFoundException {
        this.candidateService
        .saveResume(candidateId, new Document(uploadFile.getOriginalFilename(), uploadFile.getBytes(), uploadFile.getContentType()));
    }
    
    /**
     * Allow a client to download a candidate's resume
     *
     * @param candidateId   The candidate ID
     *
     * @return The resume content
     *
     * @throws CandidateNotFoundException If the candidate does not exist
     * @throws DocumentNotFoundException If the candidate does not have a resume
     */
    @RequestMapping(path = "/candidate/{id}/resume", method = RequestMethod.GET)
    public @ResponseBody HttpEntity<byte[]> downloadResume(@PathVariable("id") Integer candidateId)
    throws CandidateNotFoundException, DocumentNotFoundException {
        Document document = this.candidateService.getResume(candidateId);
        
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.parseMediaType(document.getContentType()));
        header.set("Content-Disposition", "inline; filename=" + document.getName());
        header.setContentLength(document.getLength());
        
        return new HttpEntity<byte[]>(document.getContent(), header);
    }
    
    /**
     * Delete a previously stored resume for a candidate
     *
     * @param candidateId   The candidate ID
     *
     * @throws CandidateNotFoundException   If the candidate is not found
     * @throws DocumentNotFoundException If the document is not found
     */
    @Transactional
    @RequestMapping(path = "/candidate/{id}/resume", method = RequestMethod.DELETE)
    public void deleteResume(@PathVariable("id") Integer candidateId)
    throws CandidateNotFoundException, DocumentNotFoundException {
        this.candidateService.deleteResume(candidateId);
    }
}
