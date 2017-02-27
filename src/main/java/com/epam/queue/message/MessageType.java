package com.epam.queue.message;

public enum MessageType {

    A(MessageTypePriority.HIGH),
    B(MessageTypePriority.MEDIUM),
    C(MessageTypePriority.LOW),
    D(MessageTypePriority.LOW);

    private MessageTypePriority priority;

    MessageType(MessageTypePriority priority) {
        this.priority = priority;
    }

    public MessageTypePriority getPriority() {
        return priority;
    }
}
