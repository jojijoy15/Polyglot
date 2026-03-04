package com.problems.learning.algo.simple;

public class MoveAllZeros {

    public int[] moveZeroesToFront(int[] elements) {
        int sum = 0;
        for(int element : elements){
            sum += element;
        }
        int index = elements.length -1;
        while(index > -1 & sum > 0) {
            elements[index--] = 1;
            sum -=1;
        }
        while(index > -1){
            elements[index--] = 0;
        }
        return elements;
    }

    public int[] moveZeroesToBack(int[] elements) {
        int sum = 0;
        for(int element : elements){
            sum += element;
        }
        int index = 0;
        while(index < elements.length & sum > 0) {
            elements[index++] = 1;
            sum -=1;
        }
        while(index < elements.length){
            elements[index++] = 0;
        }
        return elements;
    }
}
