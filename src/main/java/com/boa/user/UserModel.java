package com.boa.user;

import com.boa.common.PersonModel;

/**
 * This model describes a user of the system
 */
public class UserModel extends PersonModel {
    private Integer id;
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
}

