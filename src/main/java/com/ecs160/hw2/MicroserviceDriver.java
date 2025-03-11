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
        Options options = new Options();

        Option fileOption = new Option("f", "file", true, "Path to the input JSON file");
        fileOption.setRequired(false);
        options.addOption(fileOption);

        CommandLineParser clParser = new DefaultParser();

        try {
            CommandLine cmd = clParser.parse(options, args);
            filePath = cmd.hasOption("file") ? cmd.getOptionValue("file") : "input.json";

        } catch (ParseException e) {
            System.out.println("Error parsing command-line arguments: " + e.getMessage());
            System.exit(1);
        }

        try {
            PostParser pParser = new PostParser();
            List<Post> posts = pParser.parseJson(filePath);
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

    public static void processPost(Post post) throws URISyntaxException, IOException, InterruptedException {
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
            System.out.println("[DELETED]");
        }
        else{
            System.out.print(post.getContent() + " ");
            System.out.println(response.body());
        }
    }
}
