package com.problems.learning.algo.stacks;

import java.util.Stack;

public class NextSmallestElements {

    public int[] nextSmallest(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        int[] result = new int[arr.length];
        for(int i = 0; i < arr.length; i++) {
            while(!stack.isEmpty() && arr[stack.peek()] > arr[i]){ // Monotonic Increasing >
                result[stack.peek()] = arr[i];
                stack.pop();
            }
            stack.push(i);
        }
        while(!stack.isEmpty()){
            result[stack.peek()] = -1;
            stack.pop();
        }
        return result;
    }
}
