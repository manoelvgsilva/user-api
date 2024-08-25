package com.app.user.controller;

import com.app.user.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * messagecontroller.
 */
@RestController
@RequestMapping("/message")
public class MessageController {
  private final MessageService messageService;

  /**
   * messagecontroller.
   *
   * @param messageService the messageservice
   */
  @Autowired
  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  /**
   * sendmessage.
   *
   * @param message the message
   * @return message
   */
  @PostMapping
  public ResponseEntity<String> sendMessage(@RequestBody String message) {
    messageService.sendMessage(message);
    return ResponseEntity.ok().body("Mensagem enviada com sucesso: " + message);
  }
}