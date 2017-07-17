package com.boa.interview;

import java.time.LocalDateTime;

/**
 * This model describes a scheduled interview
 */
public class ScheduledInterviewModel {
    private Integer organizerId;
    private LocalDateTime scheduledTime;
    
    public Integer getOrganizerId() {
        return organizerId;
    }
    
    public void setOrganizerId(Integer organizerId) {
        this.organizerId = organizerId;
    }
    
    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }
    
    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
}
