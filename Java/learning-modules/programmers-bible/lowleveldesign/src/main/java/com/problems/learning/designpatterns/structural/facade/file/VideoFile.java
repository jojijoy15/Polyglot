package com.problems.learning.designpatterns.structural.facade.file;

public class VideoFile {
    private String fileName;

    public VideoFile(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}