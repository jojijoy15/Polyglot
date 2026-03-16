package recursion.sorting;

public class HeapSort implements Sort {

    /*
        1-based
           parent : i/2
           left child : 2 * i
           right child : 2 * i + 1
         0-based index
           parent : ( i - 1 )/2
           left child : 2 * i + 1
           right child : 2 * i + 2
     */

    @Override
    public int[] sort(int[] elements) {
        heapify(elements); //max Heap sort
        for (int end = elements.length - 1; end > 0; end--) {

            // Swap root with last element
            int temp = elements[0];
            elements[0] = elements[end];
            elements[end] = temp;

            // Restore heap property
            percolateDown(elements, 0, end);
        }
        return elements;
    }

    private void heapify(int[] elements) {
        int heapSize = elements.length/2 - 1;
        for (int i = heapSize; i >= 0; i--) {
           percolateDown(elements, i, elements.length);
        }
    }

    private void percolateDown(int[] arr, int i, int size) {
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int largest = i;

            if (left < size && arr[left] > arr[largest])
                largest = left;

            if (right < size && arr[right] > arr[largest])
                largest = right;

            if (largest == i)
                break;

            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            i = largest;
        }
    }
}
