package com.epam.queue;

import static com.epam.queue.MessageTypePriority.HIGH;
import static com.epam.queue.MessageTypePriority.LOW;
import static com.epam.queue.MessageTypePriority.MEDIUM;

public enum MessageType {

    A(HIGH), B(MEDIUM), C(LOW), D(LOW);

    private MessageTypePriority priority;

    MessageType(MessageTypePriority priority) {
        this.priority = priority;
    }

    public MessageTypePriority getPriority() {
        return priority;
    }
}
