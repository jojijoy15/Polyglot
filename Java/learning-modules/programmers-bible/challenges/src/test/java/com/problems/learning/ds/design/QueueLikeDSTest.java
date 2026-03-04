package com.problems.learning.ds.design;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;


class QueueLikeDSTest {

    private QueueLikeDS<String> queue = new QueueLikeDS<>();

    @Test
    void push() {
        assertThat(queue).isNotNull();
        assertThatCode(() -> queue.push("Hello"))
                .doesNotThrowAnyException();
        assertThat(queue.size()).isEqualTo(1);
    }

    @Test
    void pop() {
        assertThat(queue).isNotNull();
        queue.push("World");
        assertThat(queue.pop()).isEqualTo("World");
        assertThat(queue.size()).isEqualTo(0);
    }

    @Test
    void popOnEmpty() {
        assertThat(queue).isNotNull();
        assertThatThrownBy(() -> queue.pop())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Queue is empty");
        assertThat(queue.size()).isEqualTo(0);
    }

    @Test
    void popOrder() {
        assertThat(queue).isNotNull();
        queue.push("World");
        queue.push("Hello");
        assertThat(queue.pop()).isEqualTo("World");
        assertThat(queue.size()).isEqualTo(1);
    }

    @Test
    void peekNoSizeChange() {
        assertThat(queue).isNotNull();
        queue.push("World");
        queue.push("Hello");
        queue.push("Humans");
        //Second Element peek
        assertThat(queue.peek(1)).isEqualTo("Hello");
        assertThat(queue.size()).isEqualTo(3);
    }

    @Test
    void peekOutOfBounds() {
        assertThat(queue).isNotNull();
        queue.push("World");
        queue.push("Hello");
        queue.push("Humans");
        assertThatThrownBy(() -> queue.peek(10))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessage("Out of bounds: Index: 10, Size: 3");

        assertThatThrownBy(() -> queue.peekFast(10))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessage("Out of bounds: Index: 10, Size: 3");
    }

    @Test
    void peekFast() {
        assertThat(queue).isNotNull();
        queue.push("World");
        queue.push("Hello");
        queue.push("Humans");
        //Second Element peek
        assertThat(queue.peekFast(1)).isEqualTo("Hello");
        assertThat(queue.size()).isEqualTo(3);
    }

}