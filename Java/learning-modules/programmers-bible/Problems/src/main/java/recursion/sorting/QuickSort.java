package recursion.sorting;

public class QuickSort {

  public int[] sort(int[] elements) {
    quickSort(elements, 0, elements.length-1);
    return elements;
  }

  void quickSort(int[] arr, int low, int high) {
    if(low < high) {
      int partition = partition(arr, low, high);
      quickSort(arr, low,  partition - 1);
      quickSort(arr, partition + 1, high);
     }
  }

  private int partition(int[] arr, int low, int high) {
    int pivot = arr[high];
    int j = low;
    for (int i = low; i <= high; i++) {
      if(arr[i] < pivot) {
        swap(arr, i, j);
        j+=1;
      }
    }
    swap(arr, high, j);//swap the pivot at correct place
    return j;
  }

  private void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }
}
