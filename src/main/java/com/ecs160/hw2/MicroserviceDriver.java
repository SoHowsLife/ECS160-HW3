package com.ecs160.hw2;

import org.apache.commons.cli.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
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
                
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Json file not found.");
        }
    }
}
