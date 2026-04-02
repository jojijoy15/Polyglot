package com.problems.learning.designpatterns.structural.facade.client;

import com.problems.learning.designpatterns.structural.facade.compressor.VideoCompressionFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompressionClient {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(CompressionClient.class);
        VideoCompressionFacade facade = new VideoCompressionFacade();
        facade.compress("input.mp4", "avi");
    }
}