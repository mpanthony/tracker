package com.boa.controllers;

import com.boa.common.PersonModel;
import com.boa.common.TrackerException;
import com.boa.user.UserModel;
import com.boa.user.UserService;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller provides access to the users in the tracking system
 */
@RestController
@RequestMapping(value = "/api/1.0")
public class UserController {
    private UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * Retrieve information about a specific user in the system
     *
     * @param id    The user ID
     *
     * @return A model describing the user
     *
     * @throws TrackerException if an error occurs
     */
    @RequestMapping(value = "/users/{id}", method=RequestMethod.GET)
    public @ResponseBody UserModel getUser(@PathVariable("id") Integer id) throws TrackerException {
        return this.userService.getUser(id);
    }
    
    /**
     * Retrieve all users in the system
     *
     * @return  A list of models describing all the users in the system
     */
    @RequestMapping(value = "/users", method=RequestMethod.GET)
    public @ResponseBody
    List<UserModel> getUsers() {
        return this.userService.getUsers();
    }
    
    /**
     * Create a new user in the tracking system
     *
     * @param model The model describing the user to create
     *
     * @return  A model describing the created user
     *
     * @throws TrackerException if an error occurs
     */
    @Transactional
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public @ResponseBody UserModel createUser(@RequestBody PersonModel model) throws TrackerException {
        return this.userService.createUser(model);
    }
}
