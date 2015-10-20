package com.epam.queue;

import com.epam.data.InstructionMessage;

public interface InstructionQueue {

    void enqueue(InstructionMessage message);

    InstructionMessage dequeue();

    InstructionMessage peek();

    int count();

    boolean isEmpty();

}
