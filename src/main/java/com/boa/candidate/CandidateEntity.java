package com.boa.candidate;

import com.boa.interview.InterviewEntity;
import com.boa.common.PersonEntity;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This is the JPA entity mapped to the "candidate" table of the underlying database
 */
@Entity(name = "Candidate")
@Table(name = "candidate")
public class CandidateEntity extends PersonEntity {
    private boolean active;
    private List<InterviewEntity> interviews;
    private String resumeFilename;
    private byte[] resumeContent;
    private String resumeContentType;
    
    @Column(name = "active")
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    @OneToMany(targetEntity = InterviewEntity.class, mappedBy = "candidate", fetch = FetchType.LAZY)
    public List<InterviewEntity> getInterviews() {
        return interviews;
    }
    
    public void setInterviews(List<InterviewEntity> interviews) {
        this.interviews = interviews;
    }
    
    @Column(name = "resume_file_name")
    public String getResumeFilename() {
        return resumeFilename;
    }
    
    public void setResumeFilename(String resumeFilename) {
        this.resumeFilename = resumeFilename;
    }
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "resume_content")
    public byte[] getResumeContent() {
        return resumeContent;
    }
    
    public void setResumeContent(byte[] resumeContent) {
        this.resumeContent = resumeContent;
    }
    
    public String getResumeContentType() {
        return resumeContentType;
    }
    
    public void setResumeContentType(String resumeContentType) {
        this.resumeContentType = resumeContentType;
    }
}
