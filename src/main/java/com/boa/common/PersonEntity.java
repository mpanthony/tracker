package com.boa.common;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * This is a class that represents a person.  Because it is a mapper superclass, it
 * is not mapped directly to any specific table.  JPA entities can inherit from this
 * class to provide mapping to specific tables.
 */
@MappedSuperclass
public class PersonEntity {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

    @GeneratedValue
    @Id
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}
