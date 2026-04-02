package com.problems.learning.algo.hard.intervals.nonoverlapping;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NonOverlappingTest {

    NonOverlapping nonOverlapping = new NonOverlapping();

    @Test
    void maxMeetings() {
        int[][] meetingSchedule = {
                {0,6}, {1,2}, {3, 4},
                {4, 9}, {5, 7}, {5,9}, {8,9}
        };
        int maxNonOverlappingMeetings = nonOverlapping.maxMeetings(meetingSchedule);
        assertThat(maxNonOverlappingMeetings).isEqualTo(4);
    }


}