package com.ecs160.hw2;

import com.google.gson.JsonObject;

import java.util.List;

public interface Post {
    Integer getPostId();
    String getUri();
    String getCid();
    String getAuthor();
    String getContent();
    Integer getReplyCount();
    String getTimestamp();
    Integer getLikeCount();
    List<Post> getReplies();
}
