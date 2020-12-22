package hr.fer.progi.service.impl;

import hr.fer.progi.dao.NotificationRepository;
import hr.fer.progi.domain.Notification;
import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.User;
import hr.fer.progi.mappers.CreateNotificationDTO;
import hr.fer.progi.mappers.NotificationDTO;
import hr.fer.progi.service.NotificationService;
import hr.fer.progi.service.RequestService;
import hr.fer.progi.service.UserService;
import hr.fer.progi.service.exceptions.NonexistingObjectReferencedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceJpa implements NotificationService {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification createNewNotification(CreateNotificationDTO notificationDTO) {

        User user = userService.findByUsername(notificationDTO.getUsername());
        if ( user == null)
            throw new NonexistingObjectReferencedException("User with username "
                    + notificationDTO.getUsername() + " doesn't exist");

        Request request = null;
        if( notificationDTO.getRequestId() != null){
            request = requestService.getRequestById(notificationDTO.getRequestId());
            if (request==null)
                throw new NonexistingObjectReferencedException("Referenced request doesn't exist");
        }

        return notificationRepository.save(new Notification(user, notificationDTO.getMessage(), request));

    }

    @Override
    public List<Notification> getCurrentUserNotifications() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findByUsername(username);
        List<Notification> notifications = notificationRepository.findAllByUsername(username);

        //mark all current user notifications as read
        notificationRepository.updateRead(currentUser.getId());

        return notifications;
    }


    @Override
    public int getNumberOfUnreadNotifications() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return (int) notificationRepository.findAllByUsername(username)
                .stream()
                .filter(n -> !n.getIsRead())
                .count();

    }

}
