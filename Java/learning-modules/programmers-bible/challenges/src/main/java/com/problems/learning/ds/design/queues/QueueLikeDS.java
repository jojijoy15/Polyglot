package com.problems.learning.ds.design.queues;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/*
    Design a data structure with following features:
    1. push an element at the end of the data structure (O(1) time complexity)
    2. Pop the element from the beginning of the data structure( pop the earliest element added, O(1) TC)
    3. Seek an element at index i at any given state of the data structure. (O(1) TC)
*/
public class QueueLikeDS<E> {

    static class Node<E> {
        E data;
        Node<E> next;
    }

    List<Node<E>> fastAccess = new ArrayList<>();

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public void push(E e) {
        Node<E> newNode = new Node<>();
        newNode.data = e;
        if(head == null){
            head = newNode;
            tail = newNode;
        }
        else {
            tail.next = newNode;
            tail = newNode;
        }
        fastAccess.add(newNode);
        size++;
    }

    public E pop() {
        if(head == null)
            throw new NoSuchElementException("Queue is empty");
        E data = head.data;
        fastAccess.remove(0);
        head = head.next;
        size--;
        return data;
    }

    //O(n)
    public E peek(int index) {
        if(head == null)
            throw new NoSuchElementException("Queue is empty");
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Out of bounds: Index: " + index + ", Size: " + size);
        }
        Node<E> current = head;
        int currentIndex = 0;
        do {
            if(currentIndex == index)
                return current.data;
            currentIndex++;
            current = current.next;
        } while( current != null);
        return null;
    }

    //O(1)
    public E peekFast(int index) {
        if (head == null && fastAccess.isEmpty())
            throw new NoSuchElementException("Queue is empty");
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Out of bounds: Index: " + index + ", Size: " + size);
        }
        return fastAccess.get(index).data;
    }

    public int size() {
        return size;
    }

}
