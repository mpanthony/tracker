package com.boa.candidate;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.boa.common.ModelFactory;
import com.boa.common.TrackerException;
import com.boa.user.UserRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CandidateServiceImplTests {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    
    private CandidateRepository candidateRepository;
    private UserRepository userRepository;
    private ModelFactory modelFactory;
    private CandidateEntity candidateEntity;
    private CandidateModel candidateModel;
    
    CandidateService sut;
    
    @Before
    public void setUp() throws CandidateNotFoundException {
        this.candidateRepository = mock(CandidateRepository.class);
        this.userRepository = mock(UserRepository.class);
        this.modelFactory = mock(ModelFactory.class);
        
        this.candidateEntity = mock(CandidateEntity.class);
        this.candidateModel = mock(CandidateModel.class);
        
        when(this.candidateEntity.getId()).thenReturn(1);
        
        when(this.modelFactory.toModel(this.candidateEntity)).thenReturn(this.candidateModel);
        when(this.candidateModel.getId()).thenReturn(1);
    
        when(this.candidateRepository.getCandidate(1)).thenReturn(this.candidateEntity);
    
        this.sut = new CandidateServiceImpl(candidateRepository, userRepository, modelFactory);
    }
    
    @Test
    public void testMissingCandidateRepository() {
        expectedEx.expect(NullPointerException.class);
        expectedEx.expectMessage("Candidate repository required");
        
        CandidateService sut = new CandidateServiceImpl(null, this.userRepository, this.modelFactory);
    }
    
    @Test
    public void testMissingUserRepository() {
        expectedEx.expect(NullPointerException.class);
        expectedEx.expectMessage("User repository required");
        
        CandidateService sut = new CandidateServiceImpl(this.candidateRepository, null, this.modelFactory);
    }
    
    @Test
    public void testMissingModelFactory() {
        expectedEx.expect(NullPointerException.class);
        expectedEx.expectMessage("Model factory required");
        
        CandidateService sut = new CandidateServiceImpl(this.candidateRepository, this.userRepository, null);
    }
    
    @Test
    public void testFetchExistingCandidate() throws CandidateNotFoundException {
        CandidateModel model = this.sut.getCandidate(1);
        
        assertThat(model,is(notNullValue()));
        assertThat(model.getId(), is(1));
    }
    
    @Test
    public void testFetchMissingCandidate() throws TrackerException {
        when(this.candidateRepository.getCandidate(2)).thenThrow(new CandidateNotFoundException(2));
        
        this.expectedEx.expect(CandidateNotFoundException.class);
        
        CandidateModel model = this.sut.getCandidate(2);
        
        assertThat(model,is(notNullValue()));
        assertThat(model.getId(), is(1));
    }
}
