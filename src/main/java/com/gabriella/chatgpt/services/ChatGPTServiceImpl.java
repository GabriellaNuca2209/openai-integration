package com.gabriella.chatgpt.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriella.chatgpt.models.ChatGPTRequest;
import com.gabriella.chatgpt.models.ChatGPTResponse;
import com.gabriella.chatgpt.models.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChatGPTServiceImpl implements ChatGPTService {

    @Value("${api.openai.url}")
    private String url;
    @Value("${api.openai.model}")
    private String model;
    @Value("${api.openai.key}")
    private String key;

    private final ObjectMapper objectMapper;

    public ChatGPTServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String processSearch(String query) {
        ChatGPTRequest chatGPTRequest = new ChatGPTRequest();
        chatGPTRequest.setModel(model);
        chatGPTRequest.getMessages().add(new Message("user", query));

        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer " + key);

        String body;
        try {
            body = objectMapper.writeValueAsString(chatGPTRequest);
        } catch (Exception e) {
            return e.getMessage();
        }

        log.info("Body: " + body);

        try {
            StringEntity entity = new StringEntity(body);
            post.setEntity(entity);
        } catch (Exception e) {
            return "failed";
        }

        try (CloseableHttpClient httpClient = HttpClients.custom().build();
             CloseableHttpResponse response = httpClient.execute(post)) {

            String responseBody = EntityUtils.toString(response.getEntity());

            log.info("Response body: " + responseBody);
            ChatGPTResponse chatGPTResponse = objectMapper.readValue(responseBody, ChatGPTResponse.class);
            return chatGPTResponse.getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            return "failed";
        }
    }
}
