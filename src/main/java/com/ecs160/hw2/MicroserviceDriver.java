package com.ecs160.hw2;

import org.apache.commons.cli.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;

public class MicroserviceDriver {

    public static void main(String[] args) throws FileNotFoundException {
        String filePath = "input.json";
        boolean weighted = false;
        Options options = new Options();

        Option fileOption = new Option("f", "file", true, "Path to the input JSON file");
        fileOption.setRequired(false);
        options.addOption(fileOption);

        Option weightedOption = new Option("w", "weighted", false, "Use weighted average (true|false)");
        weightedOption.setRequired(false);
        options.addOption(weightedOption);

        CommandLineParser clParser = new DefaultParser();

        try {
            CommandLine cmd = clParser.parse(options, args);
            filePath = cmd.hasOption("file") ? cmd.getOptionValue("file") : "input.json";
            weighted = cmd.hasOption("weighted");
        } catch (ParseException e) {
            System.out.println("Error parsing command-line arguments: " + e.getMessage());
            System.exit(1);
        }

        PostConfig.getConfig().setWeighted(weighted);
        PostConfig.getConfig().setFileName(filePath);

        try {
            PostParser pParser = new PostParser();
            List<Post> posts = pParser.parseJson(PostConfig.getConfig().getFileName());
            List<Post> sortedPosts = posts.stream()
                    .sorted(Comparator.comparingInt(Post::getLikeCount).reversed())
                    .limit(10)
                    .toList();
            for (Post post : sortedPosts){
                processPost(post);
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Json file not found.");
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String processPost(Post post) throws URISyntaxException, IOException, InterruptedException {
        StringBuilder result = new StringBuilder();
        String postContent = post.getContent().replace("\n","\\n").replace("\"", "\\\"");
        String content = "{\"postContent\": \"" + postContent + "\"}";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest moderate = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:30001/moderate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(content, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = client.send(moderate, HttpResponse.BodyHandlers.ofString());
        if (response.body().equals("FAILED")) {
            result.append("[DELETED]\n");
        }
        else{
            result.append(post.getContent()).append(" ").append(response.body()).append("\n");
        }
        for (Post reply : post.getReplies()){
            result.append(processReply(reply));
        }
        return result.toString();
    }
    public static String processReply(Post post) throws URISyntaxException, IOException, InterruptedException {
        StringBuilder result = new StringBuilder();
        String postContent = post.getContent().replace("\n","\\n").replace("\"", "\\\"");
        String content = "{\"postContent\": \"" + postContent + "\"}";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest moderate = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:30001/moderate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(content, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = client.send(moderate, HttpResponse.BodyHandlers.ofString());
        result.append("-->");
        if (response.body().equals("FAILED")) {
            result.append("[DELETED]\n");
        }
        else{
            result.append(post.getContent()).append(" ").append(response.body()).append("\n");
        }
        return result.toString();
    }
}
