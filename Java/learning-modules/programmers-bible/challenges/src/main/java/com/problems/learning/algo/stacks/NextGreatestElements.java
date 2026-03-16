package com.problems.learning.algo.stacks;

import java.util.Stack;

public class NextGreatestElements {

    /*
        Create a new array by replacing each integer value with next
        greatest number and if not present than set it with default value i.e. -1.

        I/p : [5, 10, 6, 8, 6]
        O/p : [10, -1, 8, -1, -1]
     */
    public int[] nextGreatestElements(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        for(int i = 1; i < arr.length; i++){
            while(!stack.isEmpty() && arr[stack.peek()] <= arr[i]) { //Monotonic Decreasing Stack
                arr[stack.peek()] = arr[i];
                stack.pop();
            }
            stack.push(i);
        }
        while(!stack.isEmpty()){
            arr[stack.peek()]= -1;
            stack.pop();
        }
        return arr;
    }
}
