package com.problems.learning.algo.heaps;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MedianOfStreamTest {

    MedianOfStream medianOfStream = new MedianOfStream();

    @Test
    void findMedianNoEqual() {
        medianOfStream.addToStream(10);
        medianOfStream.addToStream(20);
        medianOfStream.addToStream(1);
        medianOfStream.addToStream(110);
        medianOfStream.addToStream(9);
        medianOfStream.addToStream(15);
        medianOfStream.addToStream(1);

        //1, 1, 9, 10
        // 15, 20, 110
        assertThat(medianOfStream.findMedian()).isEqualTo(10.0);

    }

    @Test
    void findMedianEqual() {
        medianOfStream.addToStream(10);
        medianOfStream.addToStream(20);
        medianOfStream.addToStream(1);
        medianOfStream.addToStream(110);
        medianOfStream.addToStream(9);
        medianOfStream.addToStream(15);
        medianOfStream.addToStream(1);
        medianOfStream.addToStream(2);

        //1, 1, 2, 9,
        //10, 15, 20, 110
        assertThat(medianOfStream.findMedian()).isEqualTo(9.5);
    }
}