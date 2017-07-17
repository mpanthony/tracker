package com.boa.user;

import com.boa.common.PersonModel;
import com.boa.common.TrackerException;
import java.util.List;

public interface UserRepository {
    UserEntity getUser(Integer id) throws TrackerException;
    UserEntity createUser(PersonModel model) throws TrackerException;
    List<UserEntity> getUsers();
}
