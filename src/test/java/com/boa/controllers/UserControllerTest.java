package com.boa.controllers;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.boa.common.PersonModel;
import com.boa.user.UserModel;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:create-schema.sql"})
public class UserControllerTest {
    @Autowired
    UserController userController;
    
    @Autowired
    TestRestTemplate restTemplate;
    
    @LocalServerPort
    private int port;
    
    private String url;
    
    @Before
    public void setup() {
        url = String.format("http://localhost:%d/api/1.0/users", port);
    }
    
    @Test
    public void testGetUsers() {
        List<UserModel> users = Arrays.asList(this.restTemplate.getForObject(url, UserModel[].class));
        
        assertThat(users,is(notNullValue()));
        assertThat(users.size(), is(5));
        
        assertThat(users.stream().map(UserModel::getEmail).filter(email->email.equals("pgregory@boa.com")).count(),is(1L));
    }
    
    @Test
    public void testGetExistingUser() {
        UserModel userModel = this.restTemplate.getForObject(url + "/1", UserModel.class);
        
        assertThat(userModel.getId(), is(1));
        assertThat(userModel.getFirstName(), is("Peter"));
        assertThat(userModel.getLastName(), is("Gregory"));
        assertThat(userModel.getEmail(), is("pgregory@boa.com"));
    }
    
    @Test
    public void testGetNonExistantUser() {
        ResponseEntity<UserModel> response = this.restTemplate.exchange(this.url + "/100", HttpMethod.GET, null, UserModel.class);
       
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }
    
    @Test
    public void testCreateUser() {
        PersonModel personModel = new PersonModel();
        
        personModel.setFirstName("Joe");
        personModel.setLastName("Blow");
        personModel.setEmail("jblow@testdomain.com");
        
        UserModel newUserModel = this.restTemplate.postForObject(this.url, personModel, UserModel.class);
        
        assertThat(newUserModel, is(notNullValue()));
        assertThat(newUserModel.getId(), is(notNullValue()));
        assertThat(newUserModel.getFirstName(), is(personModel.getFirstName()));
        assertThat(newUserModel.getLastName(), is(personModel.getLastName()));
        
        List<UserModel> users = Arrays.asList(this.restTemplate.getForObject(url, UserModel[].class));
        
        assertThat(users,is(notNullValue()));
        assertThat(users.size(), is(6));
        
        assertThat(users.stream().map(UserModel::getEmail).filter(email->email.equals("jblow@testdomain.com")).count(),is(1L));
    }
    
    @Test
    public void testCreateDuplicateUser() {
        PersonModel personModel = new PersonModel();
        
        personModel.setFirstName("Joe");
        personModel.setLastName("Blow");
        personModel.setEmail("pgregory@boa.com");
        
        ResponseEntity<UserModel> response= this.restTemplate.exchange(this.url, HttpMethod.POST, new HttpEntity<>(personModel), UserModel.class);
        
        assertThat(response, is(notNullValue()));
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
