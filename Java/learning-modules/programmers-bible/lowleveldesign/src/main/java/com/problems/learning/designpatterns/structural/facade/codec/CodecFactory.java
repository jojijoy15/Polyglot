package com.problems.learning.designpatterns.structural.facade.codec;

import com.problems.learning.designpatterns.structural.facade.file.VideoFile;

public class CodecFactory {
    public static Codec extract(VideoFile file) {
        System.out.println("Extracting codec from " + file.getFileName());
        return new MPEG4Codec(); // assume input is mp4
    }
}