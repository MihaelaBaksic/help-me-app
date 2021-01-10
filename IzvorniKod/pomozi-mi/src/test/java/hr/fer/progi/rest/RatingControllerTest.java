package hr.fer.progi.rest;

import hr.fer.progi.dao.RatingRepository;
import hr.fer.progi.dao.UserRepository;
import hr.fer.progi.domain.Address;
import hr.fer.progi.domain.Rating;
import hr.fer.progi.domain.User;
import hr.fer.progi.domain.UserStatus;
import hr.fer.progi.service.RatingService;
import hr.fer.progi.service.RequestService;
import hr.fer.progi.service.UserService;
import hr.fer.progi.service.impl.RatingServiceJpa;
import hr.fer.progi.wrappers.UserModelAssembler;
import org.h2.util.geometry.GeoJsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RatingController.class)
//@SpringBootTest
//@AutoConfigureMockMvc
class RatingControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;
    @MockBean
    private UserService userService;
    @MockBean
    private RequestService requestService;
    @MockBean
    private UserModelAssembler userModelAssembler;
    @MockBean
    private AppUserDetailsService appUserDetailsService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        User registeredUser = new User("Name1", "Surname1", "username1", "12345678",
                "user1@gmail.com", "0951111111",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        userService.registerUser(registeredUser);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getRatings() throws Exception {
        String username1 = "username1";
        User user1 = new User();
        user1.setUsername(username1);
        String username2 = "username2";
        User user2 = new User();
        user2.setUsername(username2);
        Rating rating1 = new Rating(2, "Rating 1", user1, user2, null);
        Rating rating2 = new Rating(3, "Rating 2", user1, user2, null);
        Rating rating3 = new Rating(4, "Rating 3", user1, user2, null);
        List<Rating> ratings = Arrays.asList(rating1, rating2, rating3);

        Mockito.when(userService.findByUsername(username1)).thenReturn(user1);
        Mockito.when(ratingService.userRatings(username1)).thenReturn(ratings);

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/of/{username}", username1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Profile("test")
    @WithMockUser(username = "username", password = "password", roles = "ROLE_USER")
//    @WithUserDetails(userDetailsServiceBeanName = "myUserDetailsService")
    void getRatingValueTest() throws Exception {
        String username = "username1";
        Mockito.when(ratingService.calculateAverageRatingForUser(username)).thenReturn(3.5);

        ResultActions resultActions = mockMvc.perform(
                get("/rating/of/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}