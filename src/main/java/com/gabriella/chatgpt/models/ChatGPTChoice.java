package com.gabriella.chatgpt.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGPTChoice {

    private int index;
    private Message message;
}
