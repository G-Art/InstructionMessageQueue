package com.epam.queue;

import com.epam.data.InstructionMessage;

import java.util.Optional;
import java.util.PriorityQueue;

public class InstructionQueue {

    private PriorityQueue<MessageWrapper> queue;

    private int orderSequence;

    public InstructionQueue() {
        this.orderSequence = 0;

        this.queue = new PriorityQueue<>((messageWrapperLeft, messageWrapperRight) -> {

            int messagePriority1 = messageWrapperLeft.getMessage().getInstructionType().getPriority();
            int messagePriority2 = messageWrapperRight.getMessage().getInstructionType().getPriority();

            if (Integer.compare(messagePriority2, messagePriority1) == 0) {
                return Integer.compare(messageWrapperLeft.getOrder(), messageWrapperRight.getOrder());
            }
            return Integer.compare(messagePriority2, messagePriority1);
        });
    }

    public void enqueue(InstructionMessage message) {
        addIntoQueue(Optional.of(message));
    }

    public InstructionMessage dequeue() {
        return queue.remove().getMessage();
    }

    public InstructionMessage peek() {
        return queue.peek().getMessage();
    }

    public int count() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    private void addIntoQueue(Optional<InstructionMessage> message) {
        MessageWrapper messageWrapper = new MessageWrapper();
        messageWrapper.setMessage(message);
        messageWrapper.setOrder(orderSequence++);
        queue.add(messageWrapper);
    }

    private class MessageWrapper {
        private Optional<InstructionMessage> message;
        private Integer order;

        public InstructionMessage getMessage() {
            return message.get();
        }

        public void setMessage(Optional<InstructionMessage> message) {
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
