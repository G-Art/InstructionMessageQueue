package com.epam.queue.impl;

import com.epam.data.InstructionMessage;
import com.epam.queue.InstructionQueue;
import com.epam.queue.PriorityType;

import java.util.Comparator;
import java.util.PriorityQueue;

public class DefaultInstructionQueue implements InstructionQueue {

    private PriorityQueue<MessageWrapper> queue;

    private static final int DEFAULT_INITIAL_CAPACITY = 11;
    private int sequence = 0;

    private class MessageWrapper {
        private InstructionMessage message;
        private Integer            order;

        public InstructionMessage getMessage() {
            return message;
        }

        public void setMessage(InstructionMessage message) {
            this.message = message;
        }

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }
    }

    public DefaultInstructionQueue() {
        this.queue = new PriorityQueue<MessageWrapper>(DEFAULT_INITIAL_CAPACITY, new Comparator<MessageWrapper>() {
            @Override
            public int compare(MessageWrapper messageWrapperLeft, MessageWrapper messageWrapperRight) {

                int pVal1 = getPriorityValue(messageWrapperLeft.getMessage());
                int pVal2 = getPriorityValue(messageWrapperRight.getMessage());

                return Integer.compare(pVal2, pVal1) == 0? Integer.compare(messageWrapperLeft.getOrder(), messageWrapperRight.getOrder()) : Integer.compare(
                        pVal2, pVal1);
            }

            private int getPriorityValue(InstructionMessage instructionMessage) {
                return PriorityType.valueOf(instructionMessage.getInstructionType()).getPriority();
            }
        });
    }


    @Override
    public void enqueue(InstructionMessage message){
        if (message == null) {
            throw new NullPointerException("Error Instruction Message shouldn't be null");
        }
        addIntoQueue(message);
    }

    @Override
    public InstructionMessage dequeue() {
        return queue.remove().getMessage();
    }

    @Override
    public InstructionMessage peek() {
        return queue.peek().getMessage();
    }

    @Override
    public int count() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    private void addIntoQueue(InstructionMessage message) {
        MessageWrapper messageWrapper = new MessageWrapper();
        messageWrapper.setMessage(message);
        messageWrapper.setOrder(sequence++);
        queue.add(messageWrapper);
    }
}
