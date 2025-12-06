package com.olimpo.olimpo.repositories;

import com.olimpo.olimpo.models.Notification;
import com.olimpo.olimpo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserOrderByFechaCreacionDesc(User user);
}
