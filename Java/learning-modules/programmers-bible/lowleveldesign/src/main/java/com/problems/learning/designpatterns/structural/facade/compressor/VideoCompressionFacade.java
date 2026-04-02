package com.problems.learning.designpatterns.structural.facade.compressor;

import com.problems.learning.designpatterns.structural.facade.bitrate.BitrateReader;
import com.problems.learning.designpatterns.structural.facade.codec.AVIcodec;
import com.problems.learning.designpatterns.structural.facade.codec.Codec;
import com.problems.learning.designpatterns.structural.facade.codec.CodecFactory;
import com.problems.learning.designpatterns.structural.facade.codec.MPEG4Codec;
import com.problems.learning.designpatterns.structural.facade.file.VideoFile;
import com.problems.learning.designpatterns.structural.facade.mixer.AudioMixer;

public class VideoCompressionFacade {

    public VideoFile compress(String fileName, String format) {

        VideoFile file = new VideoFile(fileName);

        Codec sourceCodec = CodecFactory.extract(file);

        Codec destinationCodec;
        if (format.equalsIgnoreCase("avi")) {
            destinationCodec = new AVIcodec();
        } else {
            destinationCodec = new MPEG4Codec();
        }

        VideoFile buffer = BitrateReader.decode(file, sourceCodec);
        buffer = BitrateReader.encode(buffer, destinationCodec);

        AudioMixer audioMixer = new AudioMixer();
        buffer = audioMixer.fix(buffer);

        System.out.println("Compression complete!");
        return buffer;
    }
}