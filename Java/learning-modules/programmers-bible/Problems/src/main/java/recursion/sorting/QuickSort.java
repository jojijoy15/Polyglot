package recursion.sorting;

public class QuickSort implements Sort {

  public int[] sort(int[] elements) {
    quickSort(elements, 0, elements.length-1);
    return elements;
  }

  void quickSort(int[] arr, int low, int high) {
    if(low < high) {
      int partition = lomutoPartition(arr, low, high);
      quickSort(arr, low,  partition - 1); // change this for hoare's partition to high = partition
      quickSort(arr, partition + 1, high);
     }
  }

  //Lomuto Partition
  private int lomutoPartition(int[] arr, int low, int high) {
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

  //Hoare's partition
  private int hoarePartition(int[] arr, int low, int high) {
    int pivot = arr[low];
    int i = low - 1;
    int j = high + 1;
    while(true) {
      do {
        i++;
      } while(arr[i] < pivot);

      do {
        j--;
      } while(arr[j] > pivot);

      if(i >= j) {
        return j;
      }
      swap(arr, i, j);
    }
  }

  private void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }
}
