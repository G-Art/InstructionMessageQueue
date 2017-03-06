package com.epam.queue.message;

import static com.epam.queue.message.MessageTypePriority.HIGH;
import static com.epam.queue.message.MessageTypePriority.LOW;
import static com.epam.queue.message.MessageTypePriority.MEDIUM;

public enum MessageType {

    A(HIGH),
    B(MEDIUM),
    C(LOW),
    D(LOW);

    private MessageTypePriority priority;

    MessageType(MessageTypePriority priority) {
        this.priority = priority;
    }

    public MessageTypePriority getPriority() {
        return priority;
    }
}
