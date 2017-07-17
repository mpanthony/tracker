package com.boa.common;

/**
 * This class describes a stored document, such as a resume
 */
public class Document {
    private String name;
    private byte[] content;
    private String contentType;
    
    public Document(String name, byte[] content, String contentType) {
        this.name = name;
        this.content = content;
        this.contentType = contentType;
    }
    
    public String getName() {
        return name;
    }
    
    public byte[] getContent() {
        return content;
    }
    
    public int getLength() {
        return this.content != null ? this.content.length : 0;
    }
    
    public String getContentType() {
        return contentType;
    }
}
