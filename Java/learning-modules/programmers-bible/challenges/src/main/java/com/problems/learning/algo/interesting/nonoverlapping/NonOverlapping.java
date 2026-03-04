package com.problems.learning.algo.interesting.nonoverlapping;

import com.problems.learning.algo.interesting.nonoverlapping.models.Interval;
import com.problems.learning.algo.interesting.nonoverlapping.models.Offer;

import java.util.*;

public class NonOverlapping {

    /*
       Find Maximum Number of Non-overlapping Meetings:
        Input: {{0,6}, {1,2}, {3, 4}, {4, 9}, {5, 7}, {5,9}, {8,9}}
        Output: Most meetings without overlap
    */
    public int maxMeetings(int[][] intervals) {
        List<int[]> meetingIntervals = Arrays.stream(intervals)
                .sorted(Comparator.comparingInt(a -> a[1]))
                .toList();
        int lastMeetingEnd = meetingIntervals.get(0)[1];
        int nonOverlappingMeetings = 1;
        for(int i = 0; i < meetingIntervals.size(); i++){
            int meetingStartInterval = meetingIntervals.get(i)[0];
            if(meetingStartInterval > lastMeetingEnd) {
                nonOverlappingMeetings++;
                lastMeetingEnd = meetingIntervals.get(i)[1];
            }
        }
        return nonOverlappingMeetings;
    }

    /*
    Get Lowest Prices Across Time Intervals:
        Input: List of vendor offers with (startTime, endTime, price)
        Output: Non-overlapping intervals showing lowest price at any time.
     */
    public int lowestPricePossible(List<Offer> offers) {
        Set<Integer> intervalBoundaries = new TreeSet<>();
        for(Offer offer : offers){
            intervalBoundaries.add(offer.getStartTime());
            intervalBoundaries.add(offer.getEndTime());
        }


        return 0;
    }

    private List<Interval> createBoundaries(List<Integer> boundaries) {
        List<Interval> intervals = new ArrayList<>();
        for (int i = 0; i < boundaries.size()- 1; i++) {
            Interval interval = new Interval(boundaries.get(i), boundaries.get(i + 1));
            intervals.add(interval);
        }
        return intervals;
    }

}
