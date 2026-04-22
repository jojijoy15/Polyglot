package com.problems.learning.algo.sorting;

public class MergeByGap {

    // In place merge by gap method
    public void merge(int[] first, int[] second) {
        int firstLength = first.length;
        int secondLength = second.length;

        int gap = (firstLength + secondLength + 1) / 2;

        while (gap > 0){

            int i = 0;
            int j = gap;

            while (j < firstLength + secondLength){
                int a = get(first, second, i, firstLength);
                int b = get(first, second, j, firstLength);

                if(a >= b) {
                    set(first, second, i, firstLength, b);
                    set(first, second, j, firstLength, a);
                }
                i++;
                j++;
            }
            if(gap == 1)
                break;
            gap = (gap + 1)/2;
        }
    }

    private int get(int[] first, int[] second, int index, int firstLen) {
        if(index < firstLen){
            return first[index];
        }
        return second[index - firstLen];
    }

    private void set(int[] first, int[] second, int index, int firstLen, int value) {
        if(index < firstLen ){
            first[index] = value;
            return;
        }
        second[index - firstLen] = value;
    }

}
