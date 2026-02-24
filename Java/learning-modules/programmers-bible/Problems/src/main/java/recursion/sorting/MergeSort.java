package recursion.sorting;

public class MergeSort {

  public void sort(int[] elements) {
    mergeSort(elements, 0, elements.length-1);
  }

  private void mergeSort(int[] arr, int start, int end) {
    if(end - start > 1) {
      int mid = start + (end - start)/2;
      mergeSort(arr, start, mid);
      mergeSort(arr, mid + 1, end);
      merge(arr, start, mid, end);
    }
  }

  private int[] merge(int[] arr, int start, int mid, int end) {
    int len1 = (mid - start) + 1;
    int len2 = end - mid;
    int len = Math.min(len1, len2);
    int[] arr1 = new int[len1];
    int[] arr2 = new int[len2];

    for(int i = 0; i< len1; ++i)  {
      arr1[i] = arr[start + i];
    }

    for(int i = 0; i< len2; ++i)  {
      arr2[i] = arr[mid + 1 + i];
    }

    int i = 0, j = 0, k = start; // Note: Important K to start from the partition beginning
    while(i < len && j < len) {
      if(arr1[i] <= arr2[j]) {
        arr[k++] = arr1[i++];
      } else {
        arr[k++] = arr2[j++];
      }
    }
    while(i < len1) {
      arr[k++] = arr1[i++];
    }
    while(j < len2) {
      arr[k++] = arr2[j++];
    }
    return arr;
  }

}
