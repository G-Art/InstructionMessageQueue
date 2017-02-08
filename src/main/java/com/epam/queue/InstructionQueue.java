package com.epam.queue;

import com.epam.data.InstructionMessage;

import java.util.Comparator;
import java.util.PriorityQueue;

public class InstructionQueue {

    private PriorityQueue<MessageWrapper> queue;

    private int sequence = 0;

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

    public InstructionQueue() {
        this.queue = new PriorityQueue<>(new Comparator<MessageWrapper>() {
            @Override
            public int compare(MessageWrapper messageWrapperLeft, MessageWrapper messageWrapperRight) {

                int message1 = getPriorityValue(messageWrapperLeft.getMessage());
                int message2 = getPriorityValue(messageWrapperRight.getMessage());

                return Integer.compare(message2, message1) == 0 ?
                        Integer.compare(messageWrapperLeft.getOrder(), messageWrapperRight.getOrder()) :
                        Integer.compare(message2, message1);
            }

            private int getPriorityValue(InstructionMessage instructionMessage) {
                return MessageType.valueOf(instructionMessage.getInstructionType()).getPriority();
            }
        });
    }


    public void enqueue(InstructionMessage message) {
        if (message == null) {
            throw new NullPointerException("Error: Instruction Message shouldn't be null");
        }
        addIntoQueue(message);
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

    private void addIntoQueue(InstructionMessage message) {
        MessageWrapper messageWrapper = new MessageWrapper();
        messageWrapper.setMessage(message);
        messageWrapper.setOrder(sequence++);
        queue.add(messageWrapper);
    }
}
