package com.rohit.notificationsystem.repository;

import com.rohit.notificationsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}