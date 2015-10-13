package com.epam.queue;

public enum PriorityType {

    A(10), B(5), C(0), D(0);

    private int priority;

    PriorityType(int i) {
        priority = i;
    }

    public int getPriority() {
        return priority;
    }
}
