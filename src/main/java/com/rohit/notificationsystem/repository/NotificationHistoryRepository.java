package com.rohit.notificationsystem.repository;

import com.rohit.notificationsystem.model.NotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, Long> {
}