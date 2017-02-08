package com.epam.queue;

public enum MessageType {

    A(10), B(5), C(0), D(0);

    private int priority;

    MessageType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
