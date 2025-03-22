package com.ecs160.hw2;

import java.util.List;

public abstract class PostDecorator implements Post{
    protected Post decoratedPost;
    public PostDecorator(Post decoratedPost) {
        this.decoratedPost = decoratedPost;
    }

    @Override
    public Integer getPostId() {
        return decoratedPost.getPostId();
    }

    @Override
    public String getUri() {
        return decoratedPost.getUri();
    }

    @Override
    public String getCid() {
        return decoratedPost.getCid();
    }

    @Override
    public String getAuthor() {
        return decoratedPost.getAuthor();
    }

    @Override
    public String getContent() {
        return decoratedPost.getContent();
    }

    @Override
    public Integer getReplyCount() {
        return decoratedPost.getReplyCount();
    }

    @Override
    public String getTimestamp() {
        return decoratedPost.getTimestamp();
    }

    @Override
    public Integer getLikeCount() {
        return decoratedPost.getLikeCount();
    }

    @Override
    public List<Post> getReplies() {
        return decoratedPost.getReplies();
    }

    @Override
    public void accept(PostVisitor visitor) {
        decoratedPost.accept(visitor);
    }
}
