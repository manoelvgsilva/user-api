package com.app.user.controller;

import com.app.user.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * messagecontroller.
 */
@RestController
public class MessageController {

  private final MessageService messageService;

  @Autowired
  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  /**
   * sendmessage.
   *
   * @param message the message
   */
  @PostMapping("/send")
  public void sendMessage(@RequestBody Object message) {
    messageService.sendMessage("topicName", message);
  }
}
