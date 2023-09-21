package hr.fer.progi.service;

import hr.fer.progi.domain.Notification;
import hr.fer.progi.mappers.CreateNotificationDTO;
import hr.fer.progi.mappers.RatingDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface NotificationService {

    /**
     * Creation of new notification from the DTO object
     * @param notificationDTO
     * @return newly created notification that has been stored into database
     */
    Notification createNewNotification(CreateNotificationDTO notificationDTO);

    /**
     * Listing current user notifications, with unread ones being listed first
     * @return list of notifications for currently logged in user
     */
    List<Notification> getCurrentUserNotifications();


    /**
     * Calculating number of unread notifications
     * @return number of unread notifications of current user
     */
    int getNumberOfUnreadNotifications();

    /**
     * Changes notification of handler that author has to be rated
     * from not rated to rated
     */
    void markRatingNotificationAsRated(RatingDTO ratingDTO);

    /**
     * Deletes all notifications related to request with given id
     * @param id
     */
    void updateToNullByRequestId(Long id);
}
