package com.rohit.notificationsystem.channel;

import com.rohit.notificationsystem.model.User;
import org.springframework.stereotype.Component;

@Component
public class EmailChannel implements NotificationChannel {

    @Override
    public void send(User user, String title, String body) {
        System.out.println("Sending EMAIL to " + user.getEmail());
        System.out.println("Subject: " + title);
        System.out.println("Body: " + body);
    }

    @Override
    public String getChannelType() {
        return "EMAIL";
    }
}