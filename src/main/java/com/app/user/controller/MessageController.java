package com.app.user.controller;

import com.app.user.service.MessageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

  private final MessageService messageService;

  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  @PostMapping("/send")
  public void sendMessage(@RequestBody Object message) {
    messageService.sendMessage("topicName", message);
  }
}
