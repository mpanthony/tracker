package com.boa.candidate;

import com.boa.common.PersonModel;

/**
 * This model describes a candidate, a person that is going to be interviewed
 */
public class CandidateModel extends PersonModel {
    private Integer id;
    private boolean active;
    private String resumeUrl;
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public String getResumeUrl() {
        return resumeUrl;
    }
    
    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }
}

