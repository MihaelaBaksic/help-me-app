package hr.fer.progi.rest;

import hr.fer.progi.domain.Notification;
import hr.fer.progi.mappers.CreateNotificationDTO;
import hr.fer.progi.mappers.NotificationDTO;
import hr.fer.progi.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * Creates new notification from the request body
     * @param createNotificationDTO
     * @return DTO of a newly created notification
     */
    @PostMapping("/create")
    public NotificationDTO createNotification(@RequestBody CreateNotificationDTO createNotificationDTO){
        Notification n = notificationService.createNewNotification(createNotificationDTO);
        System.out.println(n);
        return n.mapToNotificationDTO();
    }

    @GetMapping("")
    public List<NotificationDTO> getNotifications(){
        return notificationService.getCurrentUserNotifications()
                .stream().map(n -> n.mapToNotificationDTO())
                .collect(Collectors.toList());
    }

    @GetMapping("/checkUnread")
    public int checkUnreadNotifications(){
        return notificationService.getNumberOfUnreadNotifications();
    }
}
