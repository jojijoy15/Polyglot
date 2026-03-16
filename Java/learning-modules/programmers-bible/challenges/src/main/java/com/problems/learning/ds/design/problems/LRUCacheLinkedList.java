package com.problems.learning.ds.design.problems;

public class LRUCacheLinkedList<E> {

    static class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;

        public Node(E data, Node<E> next, Node<E> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<E> head;
    private Node<E> tail;

    public void addAtEnd(E data) {
        Node<E> newNode = new Node<>(data,null, null);
        if(head == null) {
            head = tail = newNode;
            return;
        }
        Node<E> temp = tail;
        while(temp.next != null) {
            temp = temp.next;
        }
        temp.next = newNode;
        newNode.prev = temp;
    }

    public E remove(E data) {
        if(head == null) {
            return null;
        }
        Node<E> current = head;
        while(current.next != null) {
            if(current.data.equals(data)) {
                E oldData = current.data;
                Node<E> prev = current.prev;
                Node<E> next = current.next;
                next.prev = prev;
                if(prev != null) {
                    prev.next = next;
                }
                return oldData;
            }
            current = current.next;
        }
        return null;
    }

    public E removeFirst() {
        if(head == null) {
            return null;
        }
        Node<E> next = head.next;
        E data = null;
        if(next != null) {
            data = head.data;
            head.next = null;
            next.prev = null;
            head = next;
        } else {
            head = null;
        }
        return data;
    }
}
