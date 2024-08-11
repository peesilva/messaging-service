package com.example.demo.controller;

import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MessageRepository messageRepository;

    @Autowired
    public MessageController(KafkaTemplate<String, String> kafkaTemplate, MessageRepository messageRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.messageRepository = messageRepository;
    }

    @PostMapping
    public ResponseEntity<String> createMessage(@RequestBody String text) {
        kafkaTemplate.send("topic-messages", text);
        return ResponseEntity.ok("Message sent to Kafka");
    }

    @GetMapping
    public ResponseEntity<String> getLastMessage() {
        Message lastMessage = messageRepository.findTopByOrderByIdDesc();
        if (lastMessage != null) {
            return ResponseEntity.ok(lastMessage.getText());
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}