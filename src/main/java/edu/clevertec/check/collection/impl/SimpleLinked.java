package edu.clevertec.check.collection.impl;

import edu.clevertec.check.collection.DescendingIterator;
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

public class SimpleLinked<T> implements List<T>, DescendingIterator<T> {

    private final ReadWriteLock loki = new ReentrantReadWriteLock();
    private final Lock stopReading = loki.readLock();
    private final Lock resolReading = loki.writeLock();

    private Node<T> fstNode;
    private Node<T> lstNode;
    private int size = 0;

    public SimpleLinked() {
        lstNode = new Node<>(null, fstNode, null);
        fstNode = new Node<>(null, null, lstNode);
    }

    @Override
    public Iterator<T> descendingIterator() {
        return new Iterator<T>() {
            int counter = size - 1;

            @Override
            public boolean hasNext() {
                return counter >= 0;
            }

            @Override
            public T next() {
                return get(counter--);
            }
        };
    }


    private class Node<T> {

        private T currentElement;
        private Node<T> nextElement;
        private Node<T> prevElement;

        /**
         * null<-prevElement, [fstNode(t=null)], nextElement ->
         * <- prevElement,[lstNode(t=null)], nextElement-> null
         */

        private Node(T currentElement, Node<T> prevElement, Node<T> nextElement) {
            this.currentElement = currentElement;
            this.nextElement = nextElement;
            this.prevElement = prevElement;
        }

        public T getCurrentElement() {
            return currentElement;
        }

        public void setCurrentElement(T currentElement) {
            this.currentElement = currentElement;
        }

        public Node<T> getNextElement() {
            return nextElement;
        }

        public void setNextElement(Node<T> nextElement) {
            this.nextElement = nextElement;
        }

        public Node<T> getPrevElement() {
            return prevElement;
        }

        public void setPrevElement(Node<T> prevElement) {
            this.prevElement = prevElement;
        }

    }

    /**
     * Appends the specified currentElement to the end of this list.
     *
     * @param t of the current item to be added to the end of the list
     */
    public void addLast(T t) {
        try {
            resolReading.lock();
            Node<T> prev = lstNode;
            prev.setCurrentElement(t);
            lstNode = new Node<>(null, prev, null);
            prev.setNextElement(lstNode);
            size++;
        } finally {
            resolReading.unlock();
        }
    }

    /**
     * Appends the specified currentElement to the end of this list.
     *
     * @param t of the current item to be added to the top of the list
     */
    public void addFirst(T t) {
        try {
            resolReading.lock();
            Node<T> next = fstNode;
            next.setCurrentElement(t);
            fstNode = new Node<>(null, null, next);
            next.setPrevElement(fstNode);
            size++;
        } finally {
            resolReading.unlock();
        }
    }

    /**
     * Return the first node in the list.
     *
     * @return the first node in the list
     */
    public Node<T> getFirstElement() {
        try {
            resolReading.lock();
            return fstNode;
        } finally {
            resolReading.unlock();
        }
    }

    /**
     * Return the last node in the list.
     *
     * @return the last node in the list
     */
    public Node<T> getLastElement() {
        try {
            resolReading.lock();
            return lstNode;
        } finally {
            resolReading.unlock();
        }
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        try {
            stopReading.lock();
            return size;
        } finally {
            stopReading.unlock();
        }
    }

    /**
     * Returns true if this list contains no elements.
     * @return true if this list contains no elements
     */
    @Override
    public boolean isEmpty() {
        try {
            stopReading.lock();
            return size == 0;
        } finally {
            stopReading.unlock();
        }
    }

    /**
     * Returns true if this list contains the specified currentElement.
     * More formally, returns true if and only if this list contains at
     * least one currentElement e such that Objects.equals(o, e).
     * @param object currentElement whose presence in this list is to be tested
     * @return true if this list contains the specified currentElement
     */
    @Override
    public boolean contains(Object object) {
        try {
            stopReading.lock();
            indexOf(object);
            return true;
        } catch (SimpleListException e) {
            return false;
        } finally {
            stopReading.unlock();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int counter = 0;

            /**
             * Returns true if the iteration has more elements.
             *
             * @return true if the iteration has more elements
             */
            @Override
            public boolean hasNext() {
                return counter < size;
            }

            /**
             * Returns the next element in the iteration.
             *
             * @return the next element in the iteration
             * @throws SimpleListException if the sheet does not contain elements
             */
            @Override
            public T next() {
                try {
                    stopReading.lock();
                    if (hasNext()) {
                        return get(counter++);
                    } else {
                        throw new SimpleListException("The list item was not found");
                    }
                } finally {
                    stopReading.unlock();
                }
            }
        };
    }


    /**
     * Returns an array containing all the elements in this list in proper
     * sequence (from first to last currentElement).
     * @return an array containing all the elements in this list in proper sequence
     */
    @Override
    public Object[] toArray() {
        try {
            stopReading.lock();
            int index = 0;
            Object[] array = new Object[size];
            Node<T> currentNode = fstNode;
            while (currentNode != null) {
                array[index++] = currentNode.currentElement;
                currentNode = currentNode.nextElement;
            }
            return array;
        } finally {
            stopReading.unlock();
        }
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new SimpleListException("Unsupported operation");
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param t element to be appended to this list
     * @return true if this collection changed as a result of the call
     */
    @Override
    public boolean add(T t) {
        try {
            resolReading.lock();
            addLast(t);
            return true;
        } finally {
            resolReading.unlock();
        }
    }


    /**
     * Removes the first occurrence of the specified currentElement from this list.
     * If this list does not contain the currentElement, it is unchanged.
     * Returns true if this list contained the specified currentElement.
     * @param object currentElement to be removed from this list, if present
     * @return true if this list contained the specified currentElement
     */
    @Override
    public boolean remove(Object object) {
        try {
            resolReading.lock();
            Node<T> currentNode = fstNode;
            if (object == null) {
                while (currentNode != null) {
                    if (currentNode.currentElement == null) {
                        deleteNode(currentNode);
                        return true;
                    }
                    currentNode = currentNode.nextElement;
                }
            } else {
                while (currentNode != null) {
                    if (object.equals(currentNode.currentElement)) {
                        deleteNode(currentNode);
                        return true;
                    }
                    currentNode = currentNode.nextElement;
                }
            }
            return false;
        } finally {
            resolReading.unlock();
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    /**
     * Appends all the elements in the specified collection to the end of this list,
     * in the order that they are returned by the specified collection's iterator.
     * @param collection collection containing elements to be added to this list
     * @return true if this list changed as a result of the call
     */
    @Override
    public boolean addAll(Collection<? extends T> collection) {
        if (collection.isEmpty()) {
            return false;
        }
        try {
            resolReading.lock();
            collection.forEach(this::add);
            return true;
        } finally {
            resolReading.unlock();
        }
    }

    /**
     * Inserts all the elements in the specified collection into this list
     * at the specified position. Shifts the currentElement currently at that position
     * and any subsequent elements to the right. The new elements will appear
     * in this list in the order that they are returned by the specified
     * collection's iterator.
     * @param index index at which to insert the first currentElement from the specified collection
     * @param collection collection containing elements to be added to this list
     * @return true if this list changed as a result of the call
     * @throws SimpleListException  if the index is out of range (0...size)
     */
    @Override
    public boolean addAll(int index, Collection<? extends T> collection) {
        if (collection.isEmpty()) {
            return false;
        }
        try {
            resolReading.lock();
            for (T element : collection) {
                add(index++, element);
            }
            return true;
        } finally {
            resolReading.unlock();
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new SimpleListException("Unsupported operation");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new SimpleListException("Unsupported operation");
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        throw new SimpleListException("Unsupported operation");
    }

    @Override
    public void sort(Comparator<? super T> c) {
        throw new SimpleListException("Unsupported operation");
    }

    @Override
    public void clear() {
        throw new SimpleListException("Unsupported operation");
    }


    /**
     * Returns the currentElement at the specified position in this list.
     * @param index index of the currentElement to return
     * @return the currentElement at the specified position in this list
     * @throws SimpleListException of the index is out of range (0...size)
     */
    @Override
    public T get(int index) {
        Node<T> target = fstNode.getNextElement();
        for (int i = 0; i < index; i++) {
            target = getNextElement(target);
        }
        return target.getCurrentElement();
    }

    private Node<T> getNextElement(Node<T> current) {
        return current.getNextElement();
    }

    /**
     * Inserts an currentElement at the specified position in this list.
     * @param index index for inserting an currentElement into the list
     * @param e currentElement to be stored at the specified position
     * @return inserted currentElement
     * @throws SimpleListException if the index is out of range (0-->size)
     */
    @Override
    public T set(int index, T e) {
        try {
            resolReading.lock();
            isCorrectIndex(index);
            getNode(index).currentElement = e;
            return e;
        } finally {
            resolReading.unlock();
        }
    }


    /**
     * Removes the currentElement at the specified position in this list.
     * Shifts any subsequent elements to the left. Returns the currentElement
     * that was removed from the list.
     * @param index the index of the currentElement to be removed
     * @return the currentElement previously at the specified position
     * @throws SimpleListException if the index is out of range (0...size)
     */
    @Override
    public T remove(int index) {
        try {
            resolReading.lock();
            Node<T> node = getNode(index);
            deleteNode(node);
            return node.currentElement;
        } finally {
            resolReading.unlock();
        }
    }

    /**
     * Returns the index of the first occurrence of the specified currentElement
     * in this list, or throw exception if this list does not contain the
     * currentElement. More formally, returns the lowest index i such that
     * Objects.equals(o, get(i)), or throw exception if there is no such index.
     * @param object currentElement to search for
     * @return the index of the first occurrence of the specified currentElement
     * in this list, or throw exception if this list does not contain the currentElement
     * @throws SimpleListException if the currentElement is not found
     */
    @Override
    public int indexOf(Object object) {
        try {
            stopReading.lock();
            int index = 0;
            Node<T> currentNode = fstNode;
            if (object == null) {
                while (currentNode != null) {
                    if (currentNode.currentElement == null) {
                        return index;
                    }
                    currentNode = currentNode.nextElement;
                    index++;
                }
            } else {
                while (currentNode != null) {
                    if (object.equals(currentNode.currentElement)) {
                        return index;
                    }
                    currentNode = currentNode.nextElement;
                    index++;
                }
            }
            throw new SimpleListException("No Element");
        } finally {
            stopReading.unlock();
        }
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new SimpleListException("Unsupported operation");
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new SimpleListException("Unsupported operation");
    }


    @Override
    public ListIterator<T> listIterator(int index) {
        throw new SimpleListException("Unsupported operation");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new SimpleListException("Unsupported operation");
    }

    @Override
    public Spliterator<T> spliterator() {
        throw new SimpleListException("Unsupported operation");
    }

    /**
     * Returns list containing an arbitrary number of elements.
     * @param objects the elements to be contained in the list
     * @param <T> the List's currentElement type
     * @return List containing the specified elements
     */
    @SafeVarargs
    public static <T> List<T> of(T... objects) {
        List<T> list = new SimpleLinked<>();
        list.addAll(Arrays.asList(objects));
        return list;
    }

    /**
     * Inserts the specified currentElement into the specified position in this list.
     * Shifts the currentElement currently in this position and any subsequent elements to the right.
     *
     * @param index index at which the specified currentElement is to be inserted
     * @param t     currentElement to be inserted
     * @throws SimpleListException if the index is out of range (0...size)
     */
    @Override
    public void add(int index, T t) {
        try {
            resolReading.lock();
            if (index == size) addLast(t);
            else {
                resolReading.lock();
                pasteNode(index, t);
            }
        } finally {
            resolReading.unlock();
        }
    }

    /**
     * Inserts a new node at the specified index and containing the passed currentElement.
     *
     * @param index the index by which the new node is inserted
     * @param t     currentElement to insert
     */
    private void pasteNode(int index, T t) {
        Node<T> indexNode = getNode(index);
        Node<T> prevNode = indexNode.prevElement;
        Node<T> freshNode = new Node<>(t, prevNode, indexNode);
        indexNode.prevElement = freshNode;
        if (prevNode == null) {
            fstNode = freshNode;
        } else {
            prevNode.nextElement = freshNode;
        }

        size++;
    }

    /**
     * Returns the node at the specified position in this list.
     *
     * @param index index of the node to return
     * @return the node at the specified position in this list
     * @throws SimpleListException if the index is out of range (0->size)
     */
    private Node<T> getNode(int index) {
        isCorrectIndex(index);
        Node<T> freshNode;
        freshNode = fstNode;
        for (int i = 0; i < index; i++) {
            freshNode = freshNode.nextElement;
        }
        return freshNode;
    }

    /**
     * Removes the transmitted node from the list.
     * @param n node to delete
     */
    private void deleteNode(Node<T> n) {
        Node<T> prevElement = n.prevElement;
        Node<T> nextElement = n.nextElement;
        if (prevElement != null) {
            prevElement.nextElement = nextElement;
            if (prevElement.nextElement == null) {
                lstNode = prevElement;
            }
        }
        if (nextElement != null) {
            nextElement.prevElement = prevElement;
            if (nextElement.prevElement == null) {
                fstNode = nextElement;
            }
        }
        size--;
    }

    /**
     * Checks the index for correctness.
     *
     * @param index the index to check
     * @throws SimpleListException if the index is out of range (0->size)
     */
    private void isCorrectIndex(int index) {
        if (index >= size || index < 0) {
            throw new SimpleListException("Index >=size, or index < 0");
        }
    }



}
