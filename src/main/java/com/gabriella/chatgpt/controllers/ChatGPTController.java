package com.gabriella.chatgpt.controllers;

import com.gabriella.chatgpt.models.SearchRequest;
import com.gabriella.chatgpt.services.ChatGPTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    public ChatGPTController(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }

    @PostMapping("/searchChatGPT")
    public ResponseEntity<String> searchChatGPT(@RequestBody SearchRequest searchRequest) {
        log.info("ChatGPT started query: " + searchRequest.getQuery());

        return ResponseEntity.ok(chatGPTService.processSearch(searchRequest.getQuery()));
    }
}
