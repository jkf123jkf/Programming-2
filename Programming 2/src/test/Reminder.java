package test;

import java.time.LocalDateTime;

public class Reminder implements Comparable<Reminder> {
    private String message;
    private LocalDateTime reminderDateTime;

    public Reminder(String message, LocalDateTime reminderDateTime) {
        this.message = message;
        this.reminderDateTime = reminderDateTime;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getReminderDateTime() {
        return reminderDateTime;
    }

    @Override
    public int compareTo(Reminder other) {
        return this.reminderDateTime.compareTo(other.reminderDateTime);
    }
}
