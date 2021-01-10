package hr.fer.progi.service.impl;

import hr.fer.progi.dao.UserRepository;
import hr.fer.progi.domain.Address;
import hr.fer.progi.domain.User;
import hr.fer.progi.domain.UserStatus;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = {UserServiceJpa.class})
//@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class UserServiceJpaTest {

//        @Autowired
    @InjectMocks
    private UserServiceJpa userServiceJpa; // System Under Test

//        @MockBean
    @Mock
    private UserRepository userRepository; // Dependencies will be mocked

//    @Before
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void listAll() {
        // PREPARATION / STUB
        User user1 = new User("Name1", "Surname1", "username1", "12345678",
                "user1@gmail.com", "0951111111",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        User user2 = new User("Name2", "Surname2", "username2", "12345678",
                "user2@gmail.com", "0952222222",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        User user3 = new User("Name3", "Surname3", "username3", "12345678",
                "user3@gmail.com", "0953333333",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);

        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(
                user1,
                user2,
                user3
        ));

        // ACTION
        List<User> allUsers = userServiceJpa.listAll();

        // ASSERTION
        assertEquals(user1, allUsers.get(0));
        assertEquals(user2, allUsers.get(1));
        assertEquals(user3, allUsers.get(2));
    }

    @Test
    void registerUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void findByUsername() {
        // PREPARATION / STUB
        User user1 = new User("Name1", "Surname1", "username1", "12345678",
                "user1@gmail.com", "0951111111",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);

        Mockito.when(userRepository.findByUsername("username1")).thenReturn(user1);

        // ACTION
        User user = userServiceJpa.findByUsername("username1");

        // ASSERTION
        assertEquals(user1, user);
    }

    @Test
    void findByUsernameNonExisting() {
        // PREPARATION / STUB
        Mockito.when(userRepository.findByUsername("nonExisting")).thenReturn(null);

        // ACTION
        User nonExistingUser = userServiceJpa.findByUsername("nonExisting");

        // ASSERTION
        assertNull(nonExistingUser);
    }

    @Test
    void deleteUser() {
    }

    @Test
    void setUserAsAdmin() {
    }

    @Test
    void blockUser() {
    }

    @Test
    void unblockUser() {
    }

    @Test
    void getStatistics() {
    }
}