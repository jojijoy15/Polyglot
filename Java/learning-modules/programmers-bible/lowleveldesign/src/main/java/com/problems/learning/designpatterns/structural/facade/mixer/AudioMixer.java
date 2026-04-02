package com.problems.learning.designpatterns.structural.facade.mixer;

import com.problems.learning.designpatterns.structural.facade.file.VideoFile;

public class AudioMixer {

    public VideoFile fix(VideoFile file) {
        System.out.println("Fixing audio...");
        return file;
    }
}