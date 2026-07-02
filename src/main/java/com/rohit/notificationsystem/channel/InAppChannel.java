package com.rohit.notificationsystem.channel;

import com.rohit.notificationsystem.model.User;
import org.springframework.stereotype.Component;

@Component
public class InAppChannel implements NotificationChannel {

    @Override
    public void send(User user, String title, String body) {
        System.out.println("Saving IN-APP notification for userId: " + user.getId());
        System.out.println("Title: " + title + " | Body: " + body);
    }

    @Override
    public String getChannelType() {
        return "IN_APP";
    }
}