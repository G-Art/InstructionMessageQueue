package com.epam.queue.message;

import static com.epam.queue.message.MessageTypePriority.*;

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
