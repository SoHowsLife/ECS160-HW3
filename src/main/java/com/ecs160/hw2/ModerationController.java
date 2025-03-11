package com.ecs160.hw2;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        return "SUCCESS";
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