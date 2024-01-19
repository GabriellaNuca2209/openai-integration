package com.gabriella.chatgpt.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatGPTRequest {

    private String model;
    private List<Message> messages = new ArrayList<>();
}
