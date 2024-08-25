package com.app.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * messageservice.
 */
@Service
public class MessageService {
  private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
  private final KafkaTemplate<String, String> kafkaTemplate;
  private static String staticTopicUser;

  /**
   * messageservice.
   *
   * @param kafkaTemplate the kafkatemplate
   * @param topicUser the topicuser
   */
  @Autowired
  public MessageService(KafkaTemplate<String, String> kafkaTemplate, @Value(
      "${topic.user}") String topicUser) {
    this.kafkaTemplate = kafkaTemplate;
    staticTopicUser = topicUser;
  }

  /**
   * sendmessage.
   *
   * @param message the message
   */
  public void sendMessage(String message) {
    logger.info("Message -> {}", message);
    this.kafkaTemplate.send(staticTopicUser, message);
  }
}