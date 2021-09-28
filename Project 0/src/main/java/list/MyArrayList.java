package list;

import java.util.Arrays;
import java.util.Iterator;

public class MyArrayList<T> implements MyListInterface<T>, Iterable<T> {
    private static final int INITIAL_CAPACITY = 10;
    private int size = 0;
    private Object[] elementData = {};

    public MyArrayList() {
        elementData = new Object[INITIAL_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(T t) {
        if (size == elementData.length) {
            ensureCapacity();
        }
        elementData[size++] = t;
    }

    @Override
    public void add(T t, int index) {
        // check to see if the array size can handle another element
        if (size == elementData.length) {
            ensureCapacity();
        }

        // check to if index provide will thorw an NullPoint Error and handle it.
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(index + " is out of bounds.");
        }

        // Starting from the end of the array, move elements up one index, until you reached the index provided.
        for (int i = size; i >= index; i--) {
            elementData[i] = elementData[i - 1];
        }//end for loop

        //Set Element into the list at the index provided. Then increase the size of the array.
        elementData[index] = t;
        size++;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size " + index);
        }
        return (T) elementData[index];
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size " + index);
        }

        for (int i = index; i < size - 1; i++) {
            elementData[i] = elementData[i + 1];
        }
        size--; // reduce size of MyArrayList after removal of element.
    }

    @Override
    public void clear() {
        for (int i = 0; i <= size; i++) {
            elementData[i] = null;
        }
        size = 0;

    }


    //Returns the index of the Object if the list contains that object.
    @Override
    public int contains(T t) {

        for (int i = 0; i < size; i++) {
            if (elementData[i] == t) {
                return i;
            }
        }
        return -1;
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;


            @Override
            public boolean hasNext() {
                if (index < size) {
                    return true;
                }
                return false;
            }

            @Override
            public T next() {
                Object t = elementData[index];
                index++;
                return (T) t;
            }
        };
    }










    // Double the size of the array.
    private void ensureCapacity() {
        int newIncreasedCapacity = elementData.length * 2;
        elementData = Arrays.copyOf(elementData, newIncreasedCapacity);
    }

    // Display all objects in the array;
    public void display() {
        if (size == 0) {
            System.out.println("There are no elements in this list");
        } else {
            for (int i = 0; i < size; i++) {
                System.out.println(elementData[i].toString());
            }
        }


    }
}
