package hr.fer.progi.rest;

import hr.fer.progi.domain.Address;
import hr.fer.progi.domain.User;
import hr.fer.progi.domain.UserStatus;
import hr.fer.progi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@WebMvcTest(controllers = UserController.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private List<User> userList;

    @BeforeEach
    void setUp() {
        this.userList = new ArrayList<>();
        this.userList.add(new User("Name1", "Surname1", "username1", "12345678",
                "user1@gmail.com", "0951111111",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED));
        this.userList.add(new User("Name2", "Surname2", "username2", "12345678",
                "user2@gmail.com", "0952222222",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED));
        this.userList.add(new User("Name3", "Surname3", "username3", "12345678",
                "user3@gmail.com", "0953333333",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED));
    }

    @Test
    void getUserByUsername() throws Exception {
        String username = "username1";
        User user = new User("Name1", "Surname1", "username1", "12345678",
                "user1@gmail.com", "0951111111",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);

        Mockito.when(userService.findByUsername(username)).thenReturn(user);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/user/{username}", username)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(username));
    }
}