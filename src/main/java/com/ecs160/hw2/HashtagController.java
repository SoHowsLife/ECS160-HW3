package com.ecs160.hw2;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.types.OllamaModelType;
import io.github.ollama4j.utils.OptionsBuilder;

import java.util.List;

@RestController
public class HashtagController {
    private final String promptTemp = "Please generate a hashtag for this social media post. Only reply with the hashtags. DO NOT ADD ANYTHING ELSE:";
    @PostMapping("/hashtag")
    public String hashtag(@RequestBody MyRequest request) {
        String host = "http://localhost:11434/";
        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setRequestTimeoutSeconds(30);
        String prompt = promptTemp + request.getPostContent();
        try {
            String modelName = OllamaModelType.LLAMA3;
            OllamaResult response = ollamaAPI.generate(modelName, prompt, false, new OptionsBuilder().build());

            return response.getResponse();

        } catch (Exception e) {
            return "#bskypost";
        }
    }

    static class MyRequest {
        private String postContent;

        public String getPostContent() {
            return postContent;
        }

        public void setPostContent(String postContent) {
            this.postContent = postContent;
        }
    }
}