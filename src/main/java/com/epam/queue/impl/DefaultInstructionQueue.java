package com.epam.queue.impl;

import com.epam.data.InstructionMessage;
import com.epam.queue.InstructionQueue;
import com.epam.validation.ValidationException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DefaultInstructionQueue implements InstructionQueue {

    private List<InstructionMessage> queue;

    private Map priority;

    private int count = 0;


    public DefaultInstructionQueue(Map priority) {
        this.queue = new LinkedList<InstructionMessage>();
        this.priority = priority;
    }

    @Override
    public void enqueue(InstructionMessage message) throws ValidationException {
            if(message == null)
                throw new NullPointerException("Error Instruction Message shouldn't be null");
            addIntoQueue(message);
    }
    @Override
    public InstructionMessage dequeue() {
        if (isEmpty())
            return null;
        count--;
        return queue.remove(0);
    }

    @Override
    public InstructionMessage peek() {
        if (isEmpty())
            return null;
        return queue.get(0);
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    private void addIntoQueue(InstructionMessage message) {
        if(isEmpty()){
            queue.add(message);
        }else {
            queue.add(findMessagePriorityPosition(message), message);
        }
        count++;
    }

    private int findMessagePriorityPosition(InstructionMessage message) {
        if(priority == null)
            return 0;

        int priorityValue = (Integer) priority.get(message.getInstructionType());
        for(InstructionMessage instructionMessage : queue){
            if((Integer)priority.get(instructionMessage.getInstructionType()) <= priorityValue)
                return queue.indexOf(instructionMessage);
        }
        return count;
    }


}
