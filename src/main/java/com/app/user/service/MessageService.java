package com.app.user.service;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * MessageService.
 */
@Service
public class MessageService {
  private final Logger logger = LoggerFactory.getLogger(MessageService.class);
  private final KafkaTemplate<String, Object> kafkaTemplate;

  @Autowired
  public MessageService(KafkaTemplate<String, Object> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMessage(String topic, Object message) {
    kafkaTemplate.send(topic, message);
    logger.info("Sent message: {}", message);
  }

  @KafkaListener(topics = "${topic.user}", groupId = "group_id")
  public void user(String message) throws IOException {
    logger.info("#### -> user message -> {}", message);
  }
}