package com.ecs160.hw2;

public class PostConfig {
    private static PostConfig config;
    private static boolean isWeighted;
    private static String fileName;


    public PostConfig() {
    }

    public PostConfig(boolean isWeighted, String fileName) {
        PostConfig.isWeighted = isWeighted;
        PostConfig.fileName = fileName;
    }

    public static PostConfig getConfig() {
        if (config == null){
            config = new PostConfig(isWeighted, fileName);
        }
        return config;
    }

    public boolean getWeighted(){
        return isWeighted;
    }

    public String getFileName(){
        return fileName;
    }

    public void setWeighted(boolean isWeighted){
        PostConfig.isWeighted = isWeighted;
    }

    public void setFileName(String fileName){
        PostConfig.fileName = fileName;
    }

}
