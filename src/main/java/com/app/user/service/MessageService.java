package com.app.user.service;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * messageservice.
 */
@Service
public class MessageService {
  private final Logger logger = LoggerFactory.getLogger(MessageService.class);

  @KafkaListener(topics = "${topic.user}", groupId = "group_id")
  public void user(String message) throws IOException {
    logger.info(String.format("#### -> user message -> %d", message));
  }
}