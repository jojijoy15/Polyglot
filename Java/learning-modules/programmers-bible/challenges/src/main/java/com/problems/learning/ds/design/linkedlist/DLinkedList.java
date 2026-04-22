package com.problems.learning.ds.design.linkedlist;

import java.util.NoSuchElementException;

public class DLinkedList<E> {

    static class DNode<E> {
        E data;
        DNode<E> next;
        DNode<E> prev;

        DNode(E data, DNode<E> prev, DNode<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    private DNode<E> head;
    private DNode<E> tail;
    private int size;

    public void add(int index, E data) {    // 0-based index
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index out of range, index: " + index + ", size: " + size);
        }
        if (index == 0) { addAtFirst(data); return; }
        if (index == size) { addAtEnd(data); return; }

        DNode<E> current = head;
        int i = 0;
        while (i < index) {
            current = current.next;
            i++;
        }
        // Insert newNode *before* current
        DNode<E> newNode = new DNode<>(data, current.prev, current);
        current.prev.next = newNode;
        current.prev = newNode;
        size++;
    }

    public void addAtFirst(E data) {
        DNode<E> newNode = new DNode<>(data,null,null);
        if(head == null){ // first element
            head = tail = newNode;
            size++;
            return;
        }
        DNode<E> currentHead = head;
        currentHead.prev = newNode;
        newNode.next= currentHead;
        head = newNode;
        size++;
    }

    public void addAtEnd(E data) {
        DNode<E> newNode = new DNode<>(data,null,null);
        if(head == null){
            head = tail = newNode;
            size++;
            return;
        } else {
            DNode<E> currentEnd = tail;
            currentEnd.next = newNode;
            newNode.prev = currentEnd;
            tail = newNode;
        }
        size++;
    }

    public E get(int index){ // 0-based
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException("index out of range, index: " + index + ", size: " + size);
        }
        DNode<E> current = head;
        int i = 0;
        while(i < index){
            i++;
            current = current.next;
        }
        return current.data;
    }

    public E removeFirst(){
        if(head == null){
            throw new NoSuchElementException("empty");
        }
        DNode<E> currentHead = head;
        if(size == 1){
            head = tail = null;
        } else {
            head = currentHead.next;
            head.prev = null;
            currentHead.next = null;
        }
        size--;
        return currentHead.data;
    }

    public E removeLast(){
        if(head == null){
            throw new NoSuchElementException("empty");
        }
        DNode<E> currentTail = tail;
        if(size == 1){
            head = tail = null;
        } else {
            tail = currentTail.prev;
            tail.next = null;
            currentTail.prev = null;
        }
        size--;
        return currentTail.data;
    }

    public E removeAt(int index){ // 0-based
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException("index out of range, index: " + index + ", size: " + size);
        }
        if(head == null){
            throw new NoSuchElementException("empty");
        }
        if(index == 0) {
            return removeFirst();
        } else if(index == size - 1){
            return removeLast();
        }
        int i = 0;
        DNode<E> current = head;
        while(i < index){
            i++;
            current = current.next;
        }
        DNode<E> prev = current.prev;
        DNode<E> next = current.next;
        prev.next = next;
        next.prev = prev;
        current.next = null;
        current.prev = null;
        size--;
        return current.data;
    }

}
