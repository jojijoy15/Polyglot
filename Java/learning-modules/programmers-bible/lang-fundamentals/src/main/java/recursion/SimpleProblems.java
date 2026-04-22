package recursion;

import java.util.Arrays;

public class SimpleProblems {

  public int calculateLength(String s) {
     return getLength(s.toCharArray(), 0, s.length());
  }

  private int getLength(char[] arr, int start, int fullLength) {
    if(arr.length == 1) {
      return 0;
    }
    return getLength(Arrays.copyOf(arr, fullLength - start), start + 1, fullLength) + 1;
  }
}
