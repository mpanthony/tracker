package com.boa.common;

import org.springframework.stereotype.Component;

/**
 * This class is used to help build URLs to system resources
 */
@Component
public class UrlBuilder {
    public String buildResumeUrl(int candidateId) {
        StringBuilder builder = new StringBuilder();
        
        builder.append("/api/1.0/candidates/");
        builder.append(candidateId);
        builder.append("/resume");
        
        return builder.toString();
    }
}
