package com.boa.user;

import com.boa.common.PersonModel;
import com.boa.common.TrackerException;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * This class wraps the management of user entities and JPA
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    EntityManager em;
    
    /**
     * Retrieve a specific user
     *
     * @param id    The user ID
     *
     * @return  The user entity
     *
     * @throws TrackerException if the user cannot be found
     */
    @Override
    public UserEntity getUser(Integer id) throws TrackerException {
        Objects.requireNonNull(id, "User ID required");
    
        UserEntity entity = this.em.find(UserEntity.class, id);
        
        if (entity == null) {
            throw new UserNotFoundException(id);
        }
        
        return entity;
    }
    
    /**
     * Create a new user account
     *
     * @param model The model describing the user to create
     *
     * @return  A user entity
     *
     * @throws TrackerException if the user account cannot be created
     */
    @Override
    public UserEntity createUser(PersonModel model) throws TrackerException {
        Objects.requireNonNull(model, "User required");

        UserEntity entity = new UserEntity();
        
        entity.setFirstName(model.getFirstName());
        entity.setLastName(model.getLastName());
        entity.setEmail(model.getEmail());
    
        try {
            this.em.persist(entity);
            this.em.flush();
            
            return entity;
        } catch (Exception e) {
            throw new TrackerException("Unable to create user " + model.getEmail(), e);
        }
    }
    
    /**
     * Retrieve all users in the system
     *
     * @return  A list of user entities
     */
    @Override
    public List<UserEntity> getUsers() {
        return this.em.createQuery("SELECT u FROM User u", UserEntity.class).getResultList();
    }
}
