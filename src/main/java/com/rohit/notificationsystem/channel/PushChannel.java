package com.rohit.notificationsystem.channel;

import com.rohit.notificationsystem.model.User;
import org.springframework.stereotype.Component;

@Component
public class PushChannel implements NotificationChannel {

    @Override
    public void send(User user, String title, String body) {
        System.out.println("Sending PUSH notification to userId: " + user.getId());
        System.out.println("Title: " + title + " | Body: " + body);
    }

    @Override
    public String getChannelType() {
        return "PUSH";
    }
}