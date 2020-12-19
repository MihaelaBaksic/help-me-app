package hr.fer.progi.dao;

import hr.fer.progi.domain.Notification;
import hr.fer.progi.domain.Rating;
import hr.fer.progi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;



public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Gets all notifications for given username
     *
     * @param username Notification user username
     * @return List of all found notifications
     */
    @Query("SELECT n FROM Notification n WHERE n.user.username = :username order by n.isRead ")
    List<Notification> findAllByUsername( @Param("username") String username);

    /**
     * Marks all requests with given username as read
     * @param id
     *
     */
    @Modifying
    @Transactional
    @Query("update Notification n set n.isRead = true where n.user.id = :id")
    void updateRead(@Param("id") long id);

}
