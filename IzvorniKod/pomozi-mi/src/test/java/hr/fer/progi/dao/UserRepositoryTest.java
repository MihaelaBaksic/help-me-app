package hr.fer.progi.dao;

import hr.fer.progi.domain.Address;
import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.User;
import hr.fer.progi.domain.UserStatus;
import hr.fer.progi.service.impl.UserServiceJpa;
import org.apache.tomcat.jni.Time;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
//@RunWith(MockitoJUnitRunner.class)
class UserRepositoryTest {

    //    @InjectMocks
    @Autowired
    private RequestRepository requestRepository;

    //    @InjectMocks
    @Autowired
    private UserRepository userRepository;

    //    @BeforeEach
    @Before
    public void setup() {
//        MockitoAnnotations.initMocks(this);

        User user1 = new User("Name1", "Surname1", "username1", "12345678",
                "user1@gmail.com", "0951111111",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        User user2 = new User("Name2", "Surname2", "username2", "12345678",
                "user2@gmail.com", "0952222222",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        User user3 = new User("Name3", "Surname3", "username3", "12345678",
                "user3@gmail.com", "0953333333",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        List<User> users = Arrays.asList(user1, user2, user3);
        userRepository.saveAll(users);

        Request request1 = new Request();
        request1.setRequestAuthor(user1);
        Request request2 = new Request();
        request2.setRequestAuthor(user1);
        Request request3 = new Request();
        request3.setRequestAuthor(user2);
        List<Request> requests = Arrays.asList(request1, request2, request3);
        requestRepository.saveAll(requests);
    }

    //    @AfterEach
    @After
    public void teardown() {
        userRepository.deleteAll();
        requestRepository.deleteAll();
    }


    @Test
    void findAllAuthoredRequests() {
        // ACTION
        List<Request> authoredRequests = userRepository.findAllAuthoredRequests("username1");

        // ASSERTION
        assertEquals(2, authoredRequests.size());
    }

    @Test
    void findAllHandledRequests() {
    }
}