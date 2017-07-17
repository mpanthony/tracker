package com.boa.user;

import static java.util.stream.Collectors.toList;

import com.boa.common.ModelFactory;
import com.boa.common.PersonModel;
import com.boa.common.TrackerException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service provides the business logic for working with users of the system
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelFactory modelFactory;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelFactory modelFactory) {
        this.userRepository = userRepository;
        this.modelFactory = modelFactory;
    }
    
    /**
     * Fetch a model describing a specific user
     *
     * @param id    The user
     *
     * @return  A model describing the user
     *
     * @throws TrackerException if the user information cannot be found
     */
    @Override
    public UserModel getUser(int id) throws TrackerException {
        return this.modelFactory.toModel(this.userRepository.getUser(id));
    }
    
    /**
     * Create a user account
     *
     * @param personModel   The model describing the user to create
     *
     * @return  A model describing the created user
     *
     * @throws TrackerException if the user account cannot be created
     */
    @Override
    public UserModel createUser(PersonModel personModel) throws TrackerException {
        return this.modelFactory.toModel(this.userRepository.createUser(personModel));
    }
    
    /**
     * Retrieve a list of models describing each user of the system
     *
     * @return  The list of user models
     */
    @Override
    public List<UserModel> getUsers() {
        return userRepository.getUsers().stream()
                .map(entity->modelFactory.toModel(entity))
                .collect(toList());
    }
}
