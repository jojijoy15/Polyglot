package com.problems.learning.designpatterns.behavioral.memento.editor;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * PROBLEM SOLVED:
 * We need to save the editor's internal state at a point in time
 * so it can be restored later — but WITHOUT exposing the editor's
 * private fields to the outside world.
 * <p>
 * Memento is an immutable snapshot. Only the Originator (EditorState)
 * that created it can read its contents — everyone else treats it as
 * an opaque token.
 */
@Getter(value = AccessLevel.PACKAGE)
public final class EditorMemento {

    private final String content;       // snapshot of typed content
    private final int cursorPosition;// snapshot of cursor position
    private final String fileName;      // snapshot of file name
    private final long timestamp;     // when this snapshot was taken

    // Package-private constructor — only EditorState (same package) can create
    EditorMemento(String content, int cursorPosition, String fileName) {
        this.content = content;
        this.cursorPosition = cursorPosition;
        this.fileName = fileName;
        this.timestamp = System.currentTimeMillis();
    }


    @Override
    public String toString() {
        return String.format("Snapshot[file='%s', cursor=%d, content='%s', time=%d]",
                fileName, cursorPosition,
                content.length() > 20 ? content.substring(0, 20) + "..." : content,
                timestamp);
    }
}