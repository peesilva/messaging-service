package com.example.demo.kafka;

import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @Autowired
    private MessageRepository messageRepository;

    @KafkaListener(topics = "topic-messages", groupId = "group-consumers")
    public void consumeMessage(String messageText) {
        Message newMessage = new Message(messageText);
        messageRepository.save(newMessage);
    }
}