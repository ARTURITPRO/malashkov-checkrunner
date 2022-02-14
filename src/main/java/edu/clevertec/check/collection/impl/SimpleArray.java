package edu.clevertec.check.collection.impl;

import edu.clevertec.check.exception.SimpleListException;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.UnaryOperator;

/**
 * <p>Collections Home Task.</p>
 * <p>Implementation of the {@link List} interface.<br/>
 *
 * @param <T> the type of elements in this list
 * @author Artur Malashkou
 * @see List
 * @see LinkedList
 * @see Lock
 * @since 1.8
 */

public class SimpleArray<T> implements List<T> {
    private final ReadWriteLock locki = new ReentrantReadWriteLock();
    private final Lock stopReading = locki.readLock();
    private final Lock resolReading = locki.writeLock();
    private T[] values;


    /**
     * Constructs an empty list with an initial capacity of zero.
     */
    @SuppressWarnings("unchecked")
    public SimpleArray() {
        values = (T[]) new Object[0];
    }

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param capacity the initial capacity of the list
     * @throws SimpleListException if the specified initial capacity
     *                             is negative
     */
    @SuppressWarnings("unchecked")
    public SimpleArray(int capacity) {
        if (capacity > 0) {
            values = (T[]) new Object[capacity];
        } else if (capacity == 0) {
            values = (T[]) new Object[0];
        } else {
            throw new SimpleListException("The size of the array cannot be less than zero");
        }
    }

    /**
     * Returns the number of values in this list.
     *
     * @return the number of values in this list
     */
    @Override
    public int size() {
        try {
            stopReading.lock();
            return values.length;
        } finally {
            stopReading.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator<>(values);
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }


    /**
     * Appends the specified currentElement to the end of this list.
     *
     * @param t currentElement to be appended to this list
     * @return true if this collection changed as a result of the call
     */
    @Override
    public boolean add(T t) {
        try {
            resolReading.lock();
            T[] temp = values;
            values = (T[]) new Object[temp.length + 1];
            System.arraycopy(temp, 0, values, 0, temp.length);
            values[values.length - 1] = t;
            return true;

        } catch (ClassCastException ex) {
            ex.printStackTrace();
        } finally {
            resolReading.unlock();
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {

    }

    @Override
    public void sort(Comparator<? super T> c) {

    }

    @Override
    public void clear() {

    }

    /**
     * Returns the currentElement at the specified position in this list.
     *
     * @param index index of the currentElement to return
     * @return the currentElement at the specified position in this list
     * @throws SimpleListException if the index is out of range (0-->size)
     */
    @Override
    public T get(int index) {
        try {
            stopReading.lock();
            return values[index];
        } finally {
            stopReading.unlock();
        }
    }

    /**
     * Inserts an currentElement at the specified position in this list.
     *
     * @param index   index for inserting an currentElement into the list
     * @param element currentElement to be stored at the specified position
     * @return inserted currentElement
     * @throws SimpleListException if the index is out of range (0...size)
     */
    @Override
    public T set(int index, T element) {
        try {
            resolReading.lock();
            values[index] = element;
            return (T) values;
        } finally {
            resolReading.unlock();
        }
    }

    /**
     * Inserts the specified currentElement into the specified position in this list.
     * Shifts the currentElement currently in this position and any subsequent values to the right.
     *
     * @param index   index at which the specified currentElement is to be inserted
     * @param element currentElement to be inserted
     * @throws SimpleListException if the index is out of range (0...size)
     */
    @Override
    public void add(int index, T element) {
        try {
            resolReading.lock();
            T[] temp = values;
            values = (T[]) new Object[temp.length + 1];
            values[values.length - 1] = temp[index];
            temp[index] = element;
            System.arraycopy(temp, 0, values, 0, temp.length);
        } finally {
            resolReading.unlock();
        }
    }

    /**
     * Removes the currentElement at the specified position in this list.
     * Shifts any subsequent values to the left. Returns the currentElement
     * that was removed from the list.
     *
     * @param index the index of the currentElement to be removed
     * @return the currentElement previously at the specified position
     * @throws SimpleListException if the index is out of range (0...size)
     */
    @Override
    public T remove(int index) {
        try {
            resolReading.lock();
            T[] temp = values;
            values = (T[]) new Object[temp.length - 1];
            System.arraycopy(temp, 0, values, 0, index);
            int amountElementAfterIndex = temp.length - index - 1;
            System.arraycopy(temp, index + 1, values, index, amountElementAfterIndex);
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        } finally {
            resolReading.unlock();
        }
        return (T) values;

    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public Spliterator<T> spliterator() {
        return null;
    }

    private class ArrayIterator<E> implements Iterator<E> {
        private int index = 0;
        private E[] values;

        ArrayIterator(E[] values) {
            this.values = values;
        }

        @Override
        public boolean hasNext() {
            return index < values.length;
        }

        @Override
        public E next() {
            return values[index++];
        }

        /**
         * Checks the index for correctness.
         *
         * @param index the index to check
         * @throws SimpleListException if the index is out of range (0...size)
         */
        private void isCorrectIndex(int index) {
            if (index >= size() || index < 0) {
                throw new SimpleListException("Your index is entered incorrectly");
            }
        }

    }
}
