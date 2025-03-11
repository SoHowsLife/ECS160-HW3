package com.ecs160.hw2;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
public class ModerationController {
    private static final List<String> BANNED_WORDS = List.of(
            "illegal", "fraud", "scam", "exploit", "dox",
            "swatting", "hack", "crypto", "bots"
    );
    @PostMapping("/moderate")
    public String moderate(@RequestBody MyRequest request) {
        // Moderation logic
        String content = request.getPostContent().toLowerCase();
        for (String banned : BANNED_WORDS){
            if(content.contains(banned)){
                return "FAILED";
            }
        }
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest hashtag = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:30002/hashtag"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"postContent\": \"" + request.getPostContent() + "\"}"))
                    .build();

            HttpResponse<String> response = client.send(hashtag, HttpResponse.BodyHandlers.ofString());
            return response.body();
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