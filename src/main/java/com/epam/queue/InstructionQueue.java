package com.epam.queue;

import com.epam.data.InstructionMessage;
import com.epam.validation.ValidationException;

public interface InstructionQueue {

    void enqueue(InstructionMessage message) throws ValidationException;

    InstructionMessage dequeue();

    InstructionMessage peek();

    int count();

    boolean isEmpty();

}
