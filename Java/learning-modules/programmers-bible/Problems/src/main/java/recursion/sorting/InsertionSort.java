package recursion.sorting;

public class InsertionSort implements Sort {

    @Override
    public int[] sort(int[] elements) {
        int i = 0; //already sorted index to begin with
        while( i < elements.length - 1) {
            int j = i+1;
            while(j > 0 && (elements[j] < elements[j-1]) ) {
                int temp = elements[j-1];
                elements[j-1] = elements[j];
                elements[j] = temp;
                j--;
            }
            i++;
        }
        return elements;
    }
}
