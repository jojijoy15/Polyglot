package com.problems.learning.ds.design.standard.ll;

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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index out of range, index: " + index + ", size: " + size);
        }
        if(head == null){ // first element
            head = tail = new DNode<>(data,null,null);
        }
        DNode<E> current = head;
        int i = 0;
        while(i < index){
            current = current.next;
            i++;
        }
        current.next = new DNode<>(data,current,null); // point new node to last
        current.next.prev = current; // point last node to previous last
        size++;
    }

    public void addAtFirst(E data) {
        DNode<E> newNode = new DNode<>(data,null,null);
        if(head == null){ // first element
            head = tail = newNode;
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
        DNode<E> nextHead = currentHead.next;
        nextHead.prev = null;
        currentHead.next = null;
        head = nextHead;
        size--;
        return currentHead.data;
    }

    public E removeLast(){
        if(head == null){
            throw new NoSuchElementException("empty");
        }
        DNode<E> newTail = tail.prev;
        DNode<E> currentTail = tail;
        currentTail.prev = null;
        newTail.next = null;
        tail = newTail;
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
