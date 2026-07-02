package com.rohit.notificationsystem.channel;

import com.rohit.notificationsystem.model.User;
import org.springframework.stereotype.Component;

@Component
public class SmsChannel implements NotificationChannel {

    @Override
    public void send(User user, String title, String body) {
        System.out.println("Sending SMS to " + user.getPhone());
        System.out.println("Message: " + title + " - " + body);
    }

    @Override
    public String getChannelType() {
        return "SMS";
    }
}