package com.boa.user;

import com.boa.common.PersonEntity;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "User")
@Table(name = "user")
public class UserEntity extends PersonEntity {
}
