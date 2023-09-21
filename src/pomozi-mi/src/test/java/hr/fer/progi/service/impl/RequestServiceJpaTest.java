package hr.fer.progi.service.impl;

import hr.fer.progi.dao.NotificationRepository;
import hr.fer.progi.dao.RatingRepository;
import hr.fer.progi.dao.RequestRepository;
import hr.fer.progi.dao.UserRepository;
import hr.fer.progi.domain.*;
import hr.fer.progi.service.NotificationService;
import hr.fer.progi.service.UserService;
import hr.fer.progi.service.exceptions.BlockingException;
import hr.fer.progi.service.exceptions.NonexistingUserReferencedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class RequestServiceJpaTest {

    @InjectMocks
    private RequestServiceJpa requestServiceJpa; // System Under Test

    @Mock
    private RequestRepository requestRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private NotificationService notificationService;
    @Mock
    private UserService userService;
    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void addRequest_RequestToAddHasMissingAttributes() {
        Request requestNull = null;
        Request requestWithoutTitle = new Request(new Date(2030, 10, 20), null, "description", new Address());
        Request requestWithoutDescription = new Request(new Date(2030, 10, 20), "title", null, new Address());
        Request requestWithoutExpirationDate = new Request(null, "title", "description", new Address());
        List<Request> requests = Arrays.asList(requestNull, requestWithoutTitle, requestWithoutDescription, requestWithoutExpirationDate);

        requests.forEach(request -> assertThrows(IllegalArgumentException.class, () -> requestServiceJpa.addRequest(request)));
    }

    @Test
    void addRequest_AuthorOfRequestIsBlockedAndBlockingExceptionIsThrown() {
        User requestAuthor = new User("Name", "Surname", "username", "12345678", "user@gmail.com", "0951111111",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.PERMABLOCK);
        Request request = new Request(new Date(2030, 10, 20), "title", "description", new Address());
        request.setRequestAuthor(requestAuthor);

        assertThrows(BlockingException.class, () -> requestServiceJpa.addRequest(request));
    }

    @Test
    void addRequest_AuthorOfRequestIsNull() {
        String requestAuthorUsername = "username";
        User requestAuthor = new User("Name", "Surname", "username", "12345678", "user@gmail.com", "0951111111",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        Request request = new Request(new Date(2030, 10, 20), "title", "description", new Address("description", 45.0, 15.0));
        Notification notification = new Notification(requestAuthor, "Notification description", request, Notification.NotificationStatus.STANDARD);
        Mockito.when(userRepository.findByUsername(requestAuthorUsername)).thenReturn(requestAuthor);
        Mockito.when(requestRepository.save(request)).thenReturn(request);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn(requestAuthorUsername);
        Request requestWithAuthor = request;
        requestWithAuthor.setRequestAuthor(requestAuthor);

        Request returnedRequest = requestServiceJpa.addRequest(request);

        assertEquals(requestWithAuthor, returnedRequest);
    }

    @Test
    void addRequest_RequestAddressHasMissingAttributes() {
        Request requestWithoutAddressDescription = new Request(new Date(2030, 10, 20), "title", "description", new Address(null, 45.0, 15.0));
        Request requestWithoutAddressXcoordinate = new Request(new Date(2030, 10, 20), "title", "description", new Address("description", null, 15.0));
        Request requestWithoutAddressYcoordinate = new Request(new Date(2030, 10, 20), "title", "description", new Address("description", 45.0, null));
        User requestAuthor = new User("Name", "Surname", "username", "12345678", "user@gmail.com", "0951111111",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        requestWithoutAddressDescription.setRequestAuthor(requestAuthor);
        requestWithoutAddressXcoordinate.setRequestAuthor(requestAuthor);
        requestWithoutAddressYcoordinate.setRequestAuthor(requestAuthor);
        List<Request> requests = Arrays.asList(requestWithoutAddressDescription, requestWithoutAddressXcoordinate, requestWithoutAddressYcoordinate);

        requests.forEach(request -> assertThrows(IllegalArgumentException.class, () -> requestServiceJpa.addRequest(request)));
    }

    @Test
    void addRequest_RequestSuccessfullyAdded() {
        User requestAuthor = new User("Name", "Surname", "username", "12345678", "user@gmail.com", "0951111111",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        Request request = new Request(new Date(2030, 10, 20), "title", "description", new Address("description", 45.0, 15.0));
        request.setRequestAuthor(requestAuthor);
        Notification notification = new Notification(requestAuthor, "Notification description", request, Notification.NotificationStatus.STANDARD);
        Mockito.when(requestRepository.save(request)).thenReturn(request);

        Request returnedRequest = requestServiceJpa.addRequest(request);

        assertEquals(request, returnedRequest);
    }


    /*
    @Test
    void deleteRequest_IsDeleted() {
        String loggedInUsername = "username";
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn(loggedInUsername);
        User loggedInUser = new User("Name", "Surname", "username", "12345678", "user@gmail.com", "0951111111",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        Mockito.when(userRepository.findByUsername(loggedInUsername)).thenReturn(loggedInUser);

        Long requestToDeleteId = Long.decode("10");
        Request requestWithId = new Request();
        Mockito.when(requestRepository.findById(requestToDeleteId)).thenReturn(java.util.Optional.of(requestWithId));


        long beforeDeleting = 5;
        long afterDeleting = 4;
        Mockito.when(requestRepository.count()).thenReturn(beforeDeleting, afterDeleting);

        boolean isDeleted = requestServiceJpa.deleteRequest(requestToDeleteId);

        assertTrue(isDeleted);
    }

    @Test
    void deleteRequest_NotDeleted() {
        Long requestToDeleteId = Long.decode("10");
        Request requestWithId = new Request();
        Mockito.when(requestRepository.findById(requestToDeleteId).get()).thenReturn(requestWithId);

        String loggedInUsername = "username";
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn(loggedInUsername);
        User loggedInUser = new User("Name", "Surname", "username", "12345678", "user@gmail.com", "0951111111",
                new Address("Zagreb", 15.0, 45.0), false, UserStatus.NOTBLOCKED);
        Mockito.when(userRepository.findByUsername(loggedInUsername)).thenReturn(loggedInUser);

        long beforeDeleting = 5;
        long afterDeleting = 5;
        Mockito.when(requestRepository.count()).thenReturn(beforeDeleting, afterDeleting);

        boolean isDeleted = requestServiceJpa.deleteRequest(requestToDeleteId);

        assertFalse(isDeleted);
    }

     */
}