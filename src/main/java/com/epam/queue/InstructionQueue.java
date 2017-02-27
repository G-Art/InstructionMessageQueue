package com.epam.queue;

import com.epam.queue.message.InstructionMessage;
import com.epam.queue.message.MessageTypePriority;

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
        queue.add(new MessageWrapper(message, orderSequence++));
    }

    public InstructionMessage dequeue() {
        return queue.poll().getMessage();
    }

    public InstructionMessage peek() {
        MessageWrapper messageWrapper = queue.peek();
        return messageWrapper != null? messageWrapper.getMessage(): null;
    }

    public int count() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }


    private final class MessageWrapper {
        private final InstructionMessage message;
        private final Integer order;

        public MessageWrapper(InstructionMessage message, Integer order) {
            this.message = message;
            this.order = order;
        }

        public InstructionMessage getMessage() {
            return message;
        }

        public Integer getOrder() {
            return order;
        }

    }
}
