package com.gabriella.chatgpt.models;

import lombok.Data;

import java.util.List;

@Data
public class ChatGPTResponse {

    List<ChatGPTChoice> choices;
}
