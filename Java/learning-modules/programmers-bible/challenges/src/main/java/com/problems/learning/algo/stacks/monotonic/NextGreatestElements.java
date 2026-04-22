package com.problems.learning.algo.stacks.monotonic;

import java.util.Stack;

public class NextGreatestElements {

    /*
        Create a new array by replacing each integer value with next
        greatest number and if not present than set it with default value i.e. -1.

        I/p : [5, 10, 6, 8, 6]
        O/p : [10, -1, 8, -1, -1]
     */
    //TODO calculate distance
    public int[] nextGreatestElements(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        for(int i = 1; i < arr.length; i++){
            while(!stack.isEmpty() && arr[stack.peek()] <= arr[i]) { //Note: Monotonic Decreasing Stack, Min at top
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

    /*
        Same logic but uses the input array itself as the stack.
        We store indices in arr[0..top] (the "processed" zone).
        A separate 'top' variable tracks the stack pointer.

        This works because once we overwrite arr[index] with the answer,
        we no longer need the original value — but we DO still need the
        index on the stack.  So we keep indices (not values) in the
        stack region arr[0..top], and use a result[] to hold answers.
     */
    public int[] nextGreatestElementsWithoutStack(int[] arr) {
        int n = arr.length;
        int[] result = new int[n];
        // use result array itself as the stack storing indices
        int top = -1;
        result[++top] = 0; // push index 0

        for (int i = 1; i < n; i++) {
            while (top >= 0 && arr[result[top]] <= arr[i]) {
                result[result[top]] = arr[i]; // answer for that index
                top--;
            }
            result[++top] = i; // push current index
        }
        // remaining indices have no greater element
        while (top >= 0) {
            result[result[top]] = -1;
            top--;
        }
        return result;
    }
}
