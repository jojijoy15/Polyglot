package com.problems.learning.algo.simple;

public class SecondSmallest {

    public int secondSmallest(int[] arr) {
        int minElement = arr[0];
        int secondMinElement = arr[0];
        for(int i = 1; i < arr.length; i++){
            if(arr[i] < minElement){
                secondMinElement = minElement;
                minElement = arr[i];
            } else if(arr[i] < secondMinElement && arr[i] != minElement){
                secondMinElement = arr[i];
            }
        }
        return secondMinElement;
    }
}
