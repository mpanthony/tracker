package com.boa.user;

import com.boa.common.PersonModel;
import com.boa.common.TrackerException;
import java.util.List;

public interface UserService {
    UserModel getUser(int id) throws TrackerException;
    UserModel createUser(PersonModel personModel) throws TrackerException;
    List<UserModel> getUsers();
}
