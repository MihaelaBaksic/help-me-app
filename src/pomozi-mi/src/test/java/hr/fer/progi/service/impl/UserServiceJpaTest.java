package hr.fer.progi.service.impl;

import hr.fer.progi.dao.UserRepository;
import hr.fer.progi.domain.Address;
import hr.fer.progi.domain.User;
import hr.fer.progi.domain.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
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

    @Mock
    private RatingServiceJpa ratingService;

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

        List<User> allUsers = userServiceJpa.listAll();

        assertEquals(user1, allUsers.get(0));
        assertEquals(user2, allUsers.get(1));
        assertEquals(user3, allUsers.get(2));
    }


    @Test
    void registerUser_infoForUserThatIsToBeRegisteredIncompleteThrowsIllegalArgumentException() {
        List<User> userWithIncompleteInformation = Arrays.asList(
                null,
                new User("Name", "Surname", "username", "12345678", null, "0951111111",
                        new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED),
                new User(null, "Surname", "username", "12345678", "user@gmail.com", "0951111111",
                        new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED),
                new User("Name", null, "username", "12345678", "user@gmail.com", "0951111111",
                        new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED),
                new User("Name", "Surname", "username", "12345678", "user@gmail.com", null,
                        new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED),
                new User("Name", "Surname", null, "12345678", "user@gmail.com", "0951111111",
                        new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED),
                new User("Name", "Surname", "username", "12345678", "user@gmail.com", "0951111111",
                        new Address(null, 15.0, 45.0), false, UserStatus.NOTBLOCKED),
                new User("Name", "Surname", "username", "12345678", "user@gmail.com", "0951111111",
                        new Address("Zagreb", null, 45.0), false, UserStatus.NOTBLOCKED),
                new User("Name", "Surname", "username", "12345678", "user@gmail.com", "0951111111",
                        new Address("Zagreb", 15.0, null), false, UserStatus.NOTBLOCKED)
        );

        userWithIncompleteInformation
                .forEach(user -> assertThrows(IllegalArgumentException.class, () -> userServiceJpa.registerUser(user)));
    }

    @Test
    void registerUser_existingUsernameInDatabaseThrowsIllegalArgumentException() {
        User user = new User("Name", "Surname", "username", "12345678", "user@gmail.com", "0951111111",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        Mockito.when(userRepository.countByUsername("username")).thenReturn(1);

        assertThrows(IllegalArgumentException.class, () -> userServiceJpa.registerUser(user));
    }

    @Test
    void registerUser_existingEmailInDatabaseThrowsIllegalArgumentException() {
        User user = new User("Name", "Surname", "username", "12345678", "user@gmail.com", "0951111111",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        Mockito.when(userRepository.countByEmail("user@gmail.com")).thenReturn(1);

        assertThrows(IllegalArgumentException.class, () -> userServiceJpa.registerUser(user));
    }

    @Test
    void registerUser_existingPhoneNumberInDatabaseThrowsIllegalArgumentException() {
        User user = new User("Name", "Surname", "username", "12345678", "user@gmail.com", "0951111111",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        Mockito.when(userRepository.countByPhoneNumber("0951111111")).thenReturn(1);

        assertThrows(IllegalArgumentException.class, () -> userServiceJpa.registerUser(user));
    }

    @Test
    void registerUser_successful() {
        User user = new User("Name", "Surname", "username", "12345678", "user@gmail.com", "0951111111",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        assertEquals(user, userServiceJpa.registerUser(user));
    }


    @Test
    void updateUser_infoForUserThatIsToBeUpdatedIncompleteThrowsIllegalArgumentException() {
        List<User> userWithIncompleteInformation = Arrays.asList(
                null,
                new User("Name", "Surname", "username", "12345678", null, "0951111111",
                        new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED),
                new User(null, "Surname", "username", "12345678", "user@gmail.com", "0951111111",
                        new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED),
                new User("Name", null, "username", "12345678", "user@gmail.com", "0951111111",
                        new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED),
                new User("Name", "Surname", "username", "12345678", "user@gmail.com", null,
                        new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED),
                new User("Name", "Surname", null, "12345678", "user@gmail.com", "0951111111",
                        new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED),
                new User("Name", "Surname", "username", "12345678", "user@gmail.com", "0951111111",
                        new Address(null, 15.0, 45.0), false, UserStatus.NOTBLOCKED),
                new User("Name", "Surname", "username", "12345678", "user@gmail.com", "0951111111",
                        new Address("Zagreb", null, 45.0), false, UserStatus.NOTBLOCKED),
                new User("Name", "Surname", "username", "12345678", "user@gmail.com", "0951111111",
                        new Address("Zagreb", 15.0, null), false, UserStatus.NOTBLOCKED)
        );

        userWithIncompleteInformation
                .forEach(user -> assertThrows(IllegalArgumentException.class, () -> userServiceJpa.updateUser(user)));
    }

    @Test
    void updateUser_successful() {
        User user = new User("Name", "Surname", "username", "12345678", "user@gmail.com", "0951111111",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        assertEquals(user, userServiceJpa.updateUser(user));
    }


    @Test
    void findByUsername_existingUsername() {
        User user1 = new User("Name1", "Surname1", "username1", "12345678",
                "user1@gmail.com", "0951111111",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);

        Mockito.when(userRepository.findByUsername("username1")).thenReturn(user1);

        User user = userServiceJpa.findByUsername("username1");

        assertEquals(user1, user);
    }

    @Test
    void findByUsername_nonExistingUsernameReturnsNull() {
        Mockito.when(userRepository.findByUsername("nonExisting")).thenReturn(null);

        User nonExistingUser = userServiceJpa.findByUsername("nonExisting");

        assertNull(nonExistingUser);
    }

    @Test
    void findByUsername_nullUsernameThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> userServiceJpa.findByUsername(null));
    }


    @Test
    void deleteUser_existingUsername() {
        String usernameToDelete = "existing_username";
        Mockito.when(userRepository.deleteByUsername(usernameToDelete)).thenReturn((long) 1);

        long deletedUsers = userServiceJpa.deleteUser(usernameToDelete);

        assertEquals(1, deletedUsers);
    }

    @Test
    void deleteUser_nonExistingUsername() {
        String usernameToDelete = "nonExisting_username";
        Mockito.when(userRepository.deleteByUsername(usernameToDelete)).thenReturn((long) 0);

        long deletedUsers = userServiceJpa.deleteUser(usernameToDelete);

        assertEquals(0, deletedUsers);
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
    void getStatistics_sixValidUsersAndThreeBestOfThemWillBeReturned() {
        User user1 = new User("Name1", "Surname1", "username1", "12345678", "user1@gmail.com", "0951111111",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        User user2 = new User("Name2", "Surname2", "username2", "12345678", "user2@gmail.com", "0952222222",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        User user3 = new User("Name3", "Surname3", "username3", "12345678", "user3@gmail.com", "0953333333",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        User user4 = new User("Name4", "Surname4", "username4", "12345678", "user4@gmail.com", "0954444444",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        User user5 = new User("Name5", "Surname5", "username5", "12345678", "user5@gmail.com", "0955555555",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        User user6 = new User("Name6", "Surname6", "username6", "12345678", "user6@gmail.com", "0956666666",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(
                user1,
                user2,
                user3,
                user4,
                user5,
                user6
        ));
        Mockito.when(ratingService.calculateAverageRatingForUser(user1.getUsername())).thenReturn(3.5);
        Mockito.when(ratingService.calculateAverageRatingForUser(user2.getUsername())).thenReturn(4.2);
        Mockito.when(ratingService.calculateAverageRatingForUser(user3.getUsername())).thenReturn(1.9);
        Mockito.when(ratingService.calculateAverageRatingForUser(user4.getUsername())).thenReturn(4.6);
        Mockito.when(ratingService.calculateAverageRatingForUser(user5.getUsername())).thenReturn(3.8);
        Mockito.when(ratingService.calculateAverageRatingForUser(user6.getUsername())).thenReturn(4.0);

        List<User> best3users = userServiceJpa.getStatistics();

        assertEquals(user4, best3users.get(0));
        assertEquals(user2, best3users.get(1));
        assertEquals(user6, best3users.get(2));
    }

    @Test
    void getStatistics_TwoValidUsersAndOnlyTwoBestOfThemWillBeReturned() {
        User user1 = new User("Name1", "Surname1", "username1", "12345678", "user1@gmail.com", "0951111111",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        User user2 = new User("Name2", "Surname2", "username2", "12345678", "user2@gmail.com", "0952222222",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);

        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(
                user1,
                user2
        ));
        Mockito.when(ratingService.calculateAverageRatingForUser(user1.getUsername())).thenReturn(3.5);
        Mockito.when(ratingService.calculateAverageRatingForUser(user2.getUsername())).thenReturn(4.2);

        List<User> best3users = userServiceJpa.getStatistics();

        assertEquals(user2, best3users.get(0));
        assertEquals(user1, best3users.get(1));
    }

    @Test
    void getStatistics_noUsersPresentWillReturnEmptyList() {
        Mockito.when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> best3users = userServiceJpa.getStatistics();

        assertEquals(0, best3users.size());
    }
}