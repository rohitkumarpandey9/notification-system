package com.rohit.notificationsystem.service;

import com.rohit.notificationsystem.channel.NotificationChannel;
import com.rohit.notificationsystem.dto.NotificationRequest;
import com.rohit.notificationsystem.model.*;
import com.rohit.notificationsystem.repository.NotificationHistoryRepository;
import com.rohit.notificationsystem.repository.UserPreferenceRepository;
import com.rohit.notificationsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPreferenceRepository preferenceRepository;

    @Autowired
    private NotificationHistoryRepository historyRepository;

    @Autowired
    private List<NotificationChannel> channels;

    private Map<String, NotificationChannel> channelMap;

    // Ye method sab channels ko ek Map me arrange kar dega: "EMAIL" -> EmailChannel object
    private Map<String, NotificationChannel> getChannelMap() {
        if (channelMap == null) {
            channelMap = channels.stream()
                    .collect(Collectors.toMap(NotificationChannel::getChannelType, c -> c));
        }
        return channelMap;
    }

    public void processNotification(NotificationRequest request) {

        // 1. User dhoondo
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        // 2. User ki preferences dhoondo
        UserPreference preference = preferenceRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Preferences not found for user id: " + user.getId()));

        // 3. Har requested channel ke liye check karo aur bhejo
        for (String channelName : request.getChannels()) {

            boolean isOptedIn = isUserOptedIn(preference, channelName);

            if (!isOptedIn) {
                saveHistory(user.getId(), request.getTitle(), channelName, NotificationStatus.FAILED);
                continue; // opt-out hai to skip kar
            }

            try {
                NotificationChannel channel = getChannelMap().get(channelName);
                if (channel == null) {
                    throw new RuntimeException("Unsupported channel: " + channelName);
                }
                channel.send(user, request.getTitle(), request.getBody());
                saveHistory(user.getId(), request.getTitle(), channelName, NotificationStatus.SUCCESS);

            } catch (Exception e) {
                saveHistory(user.getId(), request.getTitle(), channelName, NotificationStatus.FAILED);
            }
        }
    }

    private boolean isUserOptedIn(UserPreference preference, String channelName) {
        return switch (channelName) {
            case "EMAIL" -> preference.isEmailEnabled();
            case "SMS" -> preference.isSmsEnabled();
            case "PUSH" -> preference.isPushEnabled();
            case "IN_APP" -> preference.isInAppEnabled();
            default -> false;
        };
    }

    private void saveHistory(Long userId, String title, String channelName, NotificationStatus status) {
        NotificationHistory history = new NotificationHistory();
        history.setUserId(userId);
        history.setTitle(title);
        history.setChannel(ChannelType.valueOf(channelName));
        history.setStatus(status);
        history.setTimestamp(LocalDateTime.now());
        historyRepository.save(history);
    }
}