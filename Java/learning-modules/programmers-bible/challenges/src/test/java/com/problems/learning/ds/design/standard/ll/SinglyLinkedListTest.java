package com.problems.learning.ds.design.standard.ll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SinglyLinkedListTest {

    private SinglyLinkedList<String> slist;

    @BeforeEach
    void setUp() {
        this.slist = new SinglyLinkedList<>();
    }

    @Test
    void add() {
        slist.add("a");
        slist.add("b");
        slist.add("c");
        assertThat(slist.size()).isEqualTo(3);
    }

    @Test
    void removeHead() {
        slist.add("a");
        slist.add("b");
        slist.add("c");
        assertThat(slist.size()).isEqualTo(3);
        slist.remove("a");
        assertThat(slist.size()).isEqualTo(2);
    }

    @Test
    void removeMid() {
        slist.add("a");
        slist.add("b");
        slist.add("c");
        assertThat(slist.size()).isEqualTo(3);
        slist.remove("b");
        assertThat(slist.size()).isEqualTo(2);
    }

    @Test
    void removeLast() {
        slist.add("a");
        slist.add("b");
        slist.add("c");
        assertThat(slist.size()).isEqualTo(3);
        slist.remove("c");
        assertThat(slist.size()).isEqualTo(2);
    }

    @Test
    void reverseItr() {
        slist.add("a");
        slist.add("b");
        slist.add("c");
        assertThat(slist.getElements()).containsExactly("a", "b", "c");
        assertThat(slist.size()).isEqualTo(3);
        slist.reverseItr();
        assertThat(slist.size()).isEqualTo(3);
        assertThat(slist.getElements()).containsExactly("c", "b", "a");
    }

    @Test
    void reverseItrTwoElements() {
        slist.add("a");
        slist.add("b");
        assertThat(slist.getElements()).containsExactly("a", "b");
        assertThat(slist.size()).isEqualTo(2);
        slist.reverseItr();
        assertThat(slist.size()).isEqualTo(2);
        assertThat(slist.getElements()).containsExactly( "b", "a");
    }

    @Test
    void reverseRec() {
        slist.add("a");
        slist.add("b");
        slist.add("c");
        assertThat(slist.getElements()).containsExactly("a", "b", "c");
        assertThat(slist.size()).isEqualTo(3);
        slist.reverseRec();
        assertThat(slist.size()).isEqualTo(3);
        assertThat(slist.getElements()).containsExactly("c", "b", "a");

    }

    @Test
    void reverseRecTwoElements() {
        slist.add("a");
        slist.add("b");
        assertThat(slist.getElements()).containsExactly("a", "b");
        assertThat(slist.size()).isEqualTo(2);
        slist.reverseRec();
        assertThat(slist.size()).isEqualTo(2);
        assertThat(slist.getElements()).containsExactly( "b", "a");

    }
}