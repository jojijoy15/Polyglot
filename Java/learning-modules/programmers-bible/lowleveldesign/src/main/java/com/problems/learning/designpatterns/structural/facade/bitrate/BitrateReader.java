package com.problems.learning.designpatterns.structural.facade.bitrate;

import com.problems.learning.designpatterns.structural.facade.codec.Codec;
import com.problems.learning.designpatterns.structural.facade.file.VideoFile;

public class BitrateReader {

    public static VideoFile decode(VideoFile file, Codec codec) {
        System.out.println("Decoding file...");
        return file;
    }

    public static VideoFile encode(VideoFile buffer, Codec codec) {
        System.out.println("Encoding file...");
        return buffer;
    }
}
