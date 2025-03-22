package com.ecs160.hw2;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class PostAnalyzer implements PostVisitor{
    private double totalPosts;
    private double totalReplies;
    private double totalWeight;
    private int longestLength;
    private long totalInterval;

    public PostAnalyzer(){
        totalPosts = 0;
        totalReplies = 0;
        totalWeight = 0;
        longestLength = 0;
        totalInterval = 0;
    }

    @Override
    public void visit(SinglePost single) {
        totalPosts++;
        double weight = 1 + ((double) countWords(single.getContent()) / longestLength);
        totalWeight += weight;
    }

    @Override
    public void visit(Thread thread) {
        totalPosts++;
        double weight = 1 + ((double) countWords(thread.getContent()) / longestLength);
        totalWeight += weight;
        for (Post reply: thread.getReplies()){
            totalReplies++;
            totalInterval += Math.abs(Instant.parse(thread.getTimestamp()).getEpochSecond() - Instant.parse(reply.getTimestamp()).getEpochSecond());
            reply.accept(this);
        }
    }

    public double getTotalPosts() {
        if(PostConfig.getConfig().getWeighted()){
            return totalWeight;
        }
        else{
            return totalPosts;
        }
    }

    public double getAvgPosts() {
        if(PostConfig.getConfig().getWeighted()){
            return totalWeight / totalPosts;
        }
        else{
            return totalReplies / totalPosts;
        }
    }

    public String getAvgInterval() {
        Duration avgInterval = Duration.ofSeconds((long) (totalInterval/totalPosts));
        return String.format("%02d:%02d:%02d", avgInterval.toHours(), avgInterval.toMinutesPart(), avgInterval.toSecondsPart());
    }

    public void findLongest(List<Post> posts) {
        for (Post post : posts) {
            int length = countWords(post.getContent());
            if (length > longestLength) {
                longestLength = length;
            }
            if (!post.getReplies().isEmpty()) {
                findLongest(post.getReplies());
            }
        }
    }

    private int countWords(String content){
        if (content == null || content.trim().isEmpty()) {
            return 0;
        }
        return content.trim().split("\\s+").length;
    }
}
