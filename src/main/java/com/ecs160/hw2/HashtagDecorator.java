package com.ecs160.hw2;

public class HashtagDecorator extends PostDecorator{
    private String hashtag;
    public HashtagDecorator(Post decoratedPost, String hashtag) {
        super(decoratedPost);
        this.hashtag = hashtag;
    }

    public void printHashTag() {
        System.out.println(this.hashtag);
    }
}
