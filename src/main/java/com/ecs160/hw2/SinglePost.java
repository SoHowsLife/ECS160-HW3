package com.ecs160.hw2;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class SinglePost implements Post{

    private Integer postId;
    private final String uri;
    private final String cid;
    private final String author;
    private final String content;
    private final String timestamp;
    private final Integer likeCount;

    public SinglePost(){
        this.uri = "";
        this.cid = "";
        this.author = "";
        this.content = "";
        this.timestamp = "2000-01-01T00:00:00.000Z";
        this.likeCount = 0;
    }

    public SinglePost(Integer postId){
        this.postId = postId;
        this.uri = "";
        this.cid = "";
        this.author = "";
        this.content = "";
        this.timestamp = "2000-01-01T00:00:00.000Z";
        this.likeCount = 0;
    }

    public SinglePost(Integer postId, String uri, String cid, String author, String content, String timestamp, Integer likeCount) {
        this.postId = postId;
        this.uri = uri;
        this.cid = cid;
        this.author = author;
        this.content = content;
        this.timestamp = timestamp;
        this.likeCount = likeCount;
    }

    @Override
    public List<Post> getReplies() {
        return List.of();
    }

    @Override
    public void accept(PostVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Integer getPostId() {
        return postId;
    }
    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getCid() {
        return cid;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public Integer getReplyCount() {
        return 0;
    }

    @Override
    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public Integer getLikeCount() {
        return likeCount;
    }
}
