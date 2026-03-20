package com.problems.learning.ds.design.standard.ll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SinglyLinkedList<E> {

    static class SNode <E>{
        E data;
        SNode <E> next;

        SNode(E data, SNode <E> next) {
            this.data = data;
            this.next = next;
        }
    }

    private SNode<E> head;
    private int size;

    public void add(E data) {
        SNode <E> newNode = new SNode<>(data, null);
        //handle empty scenario
        if(null == head) {
            head =  newNode;
        } else {
            //handle non-empty scenario
            SNode <E> current = head;
            while(current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public void remove(E data) {
        if(null == head) {
            throw new NoSuchElementException("empty");
        }
        //remove head
        if (Objects.equals(head.data, data)) {
            SNode <E> current = head;
            head = head.next;
            current.next = null; // break the current link to next to avoid memory leak
        } else {
            for(SNode <E> current = head.next, prev = head;
                    current != null; current = current.next) {
                if (Objects.equals(current.data, data)) {
                    prev.next = current.next;   // update the chain to refer prev's next to current's next
                    current.next = null;        // break the current link to next to avoid memory leak
                    break;
                }
                prev = current;
            }
        }
        size--;
    }

    public void reverseItr() {
        if(null == head) {
            throw new NoSuchElementException("empty");
        }
        if(size == 1) {
            return;
        }
        SNode<E> prev = null;
        SNode<E> current = head;
        SNode<E> next;
        do {
            next = current.next;    // save next pointer
            current.next = prev;    // update current's next to prev pointer value
            prev = current;         // prev will be current
            current = next;         //current will now be next pointer, needed to traverse ahead
        } while (next != null);     //continue till the last node
        head = prev;                //finally update head to last node
    }

    public void reverseRec() {
        if(null == head) {
            throw new NoSuchElementException("empty");
        }
        this.head = recursiveHelper(this.head);
    }

    private SNode<E> recursiveHelper(SNode<E> head) {
        if(null == head|| head.next == null) {
            return head;
        }
        // In: 1 -> 2 -> 3 -> null

        SNode<E> newHead = recursiveHelper(head.next);  // Go till the second last Node
        // 2.next.next, is null i.e 3.next is set to 2
        // 2.next = null
        head.next.next = head;                          //update next of second last Node to current Node
        head.next = null;                               // Set current node's next to null
        // return 3, repeat now this till 2.next, starting from 1
        return newHead;                                 // return last Node as this is the new Head
    }


    public int size() {
        return this.size;
    }

    public List<E> getElements() {
        if(null == head) {
            throw new NoSuchElementException("empty");
        }
        List<E> list = new ArrayList<>();
        SNode<E> current = head;
        while(current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }
}
