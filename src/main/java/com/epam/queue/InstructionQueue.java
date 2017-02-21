package com.epam.queue;

import java.util.Optional;
import java.util.PriorityQueue;

public class InstructionQueue {

    private PriorityQueue<MessageWrapper> queue;

    private int orderSequence;

    public InstructionQueue() {
        this.orderSequence = 0;

        this.queue = new PriorityQueue<>((messageWrapperLeft, messageWrapperRight) -> {

            MessageTypePriority messagePriorityLeft = messageWrapperLeft.getMessage().getInstructionType().getPriority();
            MessageTypePriority messagePriorityRight = messageWrapperRight.getMessage().getInstructionType().getPriority();

            int comparingResult = messagePriorityLeft.compareTo(messagePriorityRight);
            if (comparingResult == 0) {
                return Integer.compare(messageWrapperLeft.getOrder(), messageWrapperRight.getOrder());
            }
            return comparingResult;
        });
    }

    public void enqueue(InstructionMessage message) {
        addIntoQueue(message);
    }

    public InstructionMessage dequeue() {
        return queue.remove().getMessage();
    }

    public InstructionMessage peek() {
        return Optional.ofNullable(queue.peek()).orElseGet(MessageWrapper::new).getMessage();
    }

    public int count() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    private void addIntoQueue(InstructionMessage message) {
        MessageWrapper messageWrapper = new MessageWrapper();
        messageWrapper.setMessage(message);
        messageWrapper.setOrder(orderSequence++);
        queue.add(messageWrapper);
    }

    private class MessageWrapper {
        private InstructionMessage message;
        private Integer order;

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
}
