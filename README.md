# Backend Notification Service

## Overview

Backend Notification Service is a centralized notification management system built using Spring Boot. The application receives notification requests through a single REST API and routes them to multiple communication channels such as Email, SMS, Push Notification, and In-App Notification.

The system respects user notification preferences and maintains a complete notification history for auditing purposes.

---

## Architecture

The application follows a layered architecture:

```text
Client
   |
   v
NotificationController
   |
   v
NotificationService
   |
   +------------------------+
   |                        |
   v                        v
User Repository      Preference Repository
   |
   v
Channel Router (Strategy Pattern)
   |
   +---- EmailChannel
   +---- SmsChannel
   +---- PushChannel
   +---- InAppChannel
   |
   v
NotificationHistory Repository
```

---

## Backend Flow

### 1. Notification Request

A client sends a notification request containing:

* User ID
* Notification Title
* Notification Body
* Target Channels

Example:

```json
{
  "userId": 1,
  "title": "Loan Approved",
  "body": "Your gold loan has been approved.",
  "channels": ["EMAIL", "SMS", "PUSH"]
}
```

---

### 2. Request Validation

The request is validated using Bean Validation annotations.

Validations include:

* User ID must be present
* Title must not be blank
* Body must not be blank
* Channels list must not be empty

Invalid requests return HTTP 400 Bad Request.

---

### 3. User Lookup

The service fetches the user from the database using the provided user ID.

If the user does not exist, an exception is returned.

---

### 4. Preference Verification

User notification preferences are loaded from the database.

Supported preferences:

* Email Enabled
* SMS Enabled
* Push Enabled
* In-App Enabled

If a requested channel is disabled for the user, notification delivery is skipped.

---

### 5. Channel Routing

The application uses the Strategy Pattern.

Each notification channel implements a common interface:

```java
public interface NotificationChannel {
    void send(User user, String title, String body);
    String getChannelType();
}
```

Available implementations:

* EmailChannel
* SmsChannel
* PushChannel
* InAppChannel

The service dynamically selects the correct implementation based on the requested channel.

---

### 6. Notification Dispatch

Selected channel implementations simulate notification delivery.

Examples:

* Email notification logged to console
* SMS notification logged to console
* Push notification logged to console
* In-App notification stored as simulation

This approach satisfies the assignment requirement of using mock providers.

---

### 7. Notification History Logging

Every notification attempt is stored in the Notification History table.

Stored information:

* User ID
* Notification Title
* Channel
* Delivery Status
* Timestamp

Possible statuses:

* SUCCESS
* FAILED

This provides basic auditing and tracking capability.

## Database Design

### User

Stores basic user information.

Fields:

* id
* name
* email
* phone



### UserPreference

Stores channel subscription settings.

Fields:

* id
* emailEnabled
* smsEnabled
* pushEnabled
* inAppEnabled
* user_id



### NotificationHistory

Stores notification delivery records.

Fields:

* id
* userId
* title
* channel
* status
* timestamp

---

## Design Patterns Used

### Strategy Pattern

Implemented for notification channels.

Benefits:

* Easy to add new channels
* Loose coupling
* Better maintainability
* Open for extension, closed for modification

## Technology Stack

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* MySQL
* Hibernate
* Lombok
* Maven
* Bean Validation


## Key Features

* Unified Notification API
* Multi-channel Notification Support
* User Preference Management
* Notification History Tracking
* Strategy Pattern Based Routing
* Validation and Exception Handling
* Extensible Architecture
* Database Persistence with JPA


## Future Improvements

* Real Email Integration using JavaMailSender
* SMS Integration using Twilio
* Firebase Push Notifications
* Retry Mechanism
* Asynchronous Processing using Kafka/RabbitMQ
* Notification Templates
* Rate Limiting
* Distributed Notification Processing
