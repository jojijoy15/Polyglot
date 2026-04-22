package com.problems.learning.algo.search.binarysearch;

public class MedianSortedArrays {

    public double findMedian(int[] first, int[] second) {
        int totalLength = first.length + second.length;
        int half = (totalLength + 1)/2;
        //First is the smaller
        if(first.length > second.length){
            int[] temp = first;
            first = second;
            second = temp;
        }
        int left = 0;
        int right = first.length; // smaller array
        while(left <= right) {
            int i = (left + right)/2; //first partition
            int j = half - i; //second partition

            int lowFirst = i > 0 ? first[i - 1] : Integer.MIN_VALUE;
            int highFirst = i < first.length ? first[i] : Integer.MAX_VALUE;
            int lowSecond = j > 0 ? second[j - 1] : Integer.MIN_VALUE;
            int highSecond = j < second.length ? second[j] : Integer.MAX_VALUE;

            if ((lowFirst <= highSecond) && (lowSecond <= highFirst)) {
                if( totalLength % 2 != 0 ){
                    return Math.max(lowFirst, lowSecond);
                }
                return (Math.max(lowFirst, lowSecond)
                        + Math.min(highFirst, highSecond)) / 2.0 ;
            } else if( lowFirst > highSecond) {
                right = i - 1;
            } else {
                left = i + 1;
            }

        }
        return -1;

    }
}
