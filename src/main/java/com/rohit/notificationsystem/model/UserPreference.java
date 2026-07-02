package com.rohit.notificationsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_preferences")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean emailEnabled = true;

    private boolean smsEnabled = true;

    private boolean pushEnabled = true;

    private boolean inAppEnabled = true;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}