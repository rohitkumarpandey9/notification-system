package com.rohit.notificationsystem.channel;

import com.rohit.notificationsystem.model.User;

public interface NotificationChannel {

    void send(User user, String title, String body);

    String getChannelType();
}