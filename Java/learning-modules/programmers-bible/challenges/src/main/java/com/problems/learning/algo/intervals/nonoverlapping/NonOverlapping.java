package com.problems.learning.algo.intervals.nonoverlapping;

import java.util.*;

public class NonOverlapping {

    /*
       Find Maximum Number of Non-overlapping Meetings:
        Input: {{0,6}, {1,2}, {3, 4}, {4, 9}, {5, 7}, {5,9}, {8,9}}
        Output: Most meetings without overlap
    */
    public int maxMeetings(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));
        int lastMeetingEnd = intervals[0][1];
        int nonOverlappingMeetings = 1;
        for(int i = 0; i < intervals.length; i++){
            int meetingStartInterval = intervals[i][0];
            if(meetingStartInterval > lastMeetingEnd) {
                nonOverlappingMeetings++;
                lastMeetingEnd = intervals[i][1];
            }
        }
        return nonOverlappingMeetings;
    }


}
