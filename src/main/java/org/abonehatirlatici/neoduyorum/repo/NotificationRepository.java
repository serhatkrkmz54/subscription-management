package org.abonehatirlatici.neoduyorum.repo;

import org.abonehatirlatici.neoduyorum.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
