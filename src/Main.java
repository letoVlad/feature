import java.util.Arrays;
import java.util.Comparator;


public class Main {
    public static void main(String[] args) {
        CustomArrayList<Integer> test = new CustomArrayList<>();

       test.add(3);
       test.add(4);
       test.add(7);
       test.add(2);
       test.add(1);

        for (int i = 0; i < test.size(); i++) {
            System.out.println(test.get(i));
        }
        test.sort(Comparator.naturalOrder());
        System.out.println("-----------");
        for (int i = 0; i < test.size(); i++) {
            System.out.println(test.get(i));
        }
    }
}

class CustomArrayList<E> {

    private final int CAPACITY = 5;
    private int size = 0;
    private Object[] array;

    public CustomArrayList() {
        this.array = new Object[CAPACITY];
    }

    public void add(E element) {
        if (size >= array.length) {
            increasingArray();
        }
        array[size] = element;
        size++;
    }

    private void increasingArray() {
        if (size == array.length) {
            int newSize = array.length * 2;
            Object[] newArray = new Object[newSize];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
    }

    private void increasingArray(int minSize) {
        if (minSize > array.length) {
            int newSize = Math.max(array.length * 2, minSize);
            Object[] newArray = new Object[newSize];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
    }

    public void add(int index, E element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (size >= array.length) {
            increasingArray();
        }

        if (size - index > 0) {
            System.arraycopy(array, index, array, index + 1, size - index);
        }

        array[index] = element;
        size++;
    }

    public int size() {
        return size;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    public E get(int index) {
        return (E) array[index];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        System.arraycopy(array, index + 1, array, index, size - index - 1);
        size--;
    }

    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(array[i])) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean addAll(CustomArrayList<E> collection) {
        if (collection.isEmpty()) {
            return false;
        }
        int numNew = collection.size();
        increasingArray(size + numNew);

        Object[] a = collection.toArray();
        System.arraycopy(a, 0, array, size, numNew);
        size += numNew;
        return true;
    }

    private Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    public void sort(Comparator<? super E> c) {
        if (size > 1) {
            Object[] aux = new Object[size];
            mergeSort(0, size - 1, c, aux);
        }
    }

    private void mergeSort(int low, int high, Comparator<? super E> c, Object[] aux) {
        if (low < high) {
            int middle = low + (high - low) / 2;
            mergeSort(low, middle, c, aux);
            mergeSort(middle + 1, high, c, aux);
            merge(low, middle, high, c, aux);
        }
    }

    private void merge(int low, int middle, int high, Comparator<? super E> c, Object[] aux) {
        System.arraycopy(array, low, aux, low, high - low + 1);

        int i = low;
        int j = middle + 1;
        int k = low;

        while (i <= middle && j <= high) {
            if (c.compare((E) aux[i], (E) aux[j]) <= 0) {
                array[k++] = aux[i++];
            } else {
                array[k++] = aux[j++];
            }
        }

        System.arraycopy(aux, i, array, k, middle - i + 1);
    }

}
