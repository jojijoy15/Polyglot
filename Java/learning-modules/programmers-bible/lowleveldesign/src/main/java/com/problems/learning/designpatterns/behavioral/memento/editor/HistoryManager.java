package com.problems.learning.designpatterns.behavioral.memento.editor;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * PROBLEM SOLVED:
 * Someone needs to STORE and MANAGE the list of snapshots for
 * undo/redo — but this manager should NOT know anything about
 * the editor's internals.
 * <p>
 * The Caretaker stores Mementos as opaque objects.
 * It never reads their content — it just pushes/pops them
 * and hands them back to the Originator when needed.
 * <p>
 * This cleanly separates:
 * "what to save"    → Originator (EditorState)
 * "when to save it" → Client (Main)
 * "where to store it" → Caretaker (HistoryManager)
 */
public class HistoryManager {

    // Undo stack — past states
    private final Deque<EditorMemento> undoStack = new ArrayDeque<>();

    // Redo stack — states that were undone
    private final Deque<EditorMemento> redoStack = new ArrayDeque<>();

    /**
     * PROBLEM SOLVED:
     * Every time the editor makes a meaningful change, push a snapshot.
     * The manager doesn't care what's inside — just stores it.
     * Pushing a new state clears the redo stack (same as real editors).
     */
    public void push(EditorMemento memento) {
        undoStack.push(memento);
        redoStack.clear(); // new action invalidates redo history
        System.out.println("📚 History    : undoStack=" + undoStack.size()
                + ", redoStack=" + redoStack.size());
    }

    /**
     * Pop from undo → returns the PREVIOUS state.
     * Current state gets pushed to redo so it can be re-applied.
     */
    public EditorMemento undo(EditorMemento currentState) {
        if (undoStack.isEmpty()) {
            System.out.println("⚠️  Nothing to undo");
            return currentState;
        }
        redoStack.push(currentState); // save current for redo
        EditorMemento previous = undoStack.pop();
        System.out.println("↩️  Undo       : undoStack=" + undoStack.size()
                + ", redoStack=" + redoStack.size());
        return previous;
    }

    /**
     * Pop from redo → reapply a state that was previously undone.
     * Current state gets pushed back to undo stack.
     */
    public EditorMemento redo(EditorMemento currentState) {
        if (redoStack.isEmpty()) {
            System.out.println("⚠️  Nothing to redo");
            return currentState;
        }
        undoStack.push(currentState); // save current for undo
        EditorMemento next = redoStack.pop();
        System.out.println("↪️  Redo       : undoStack=" + undoStack.size()
                + ", redoStack=" + redoStack.size());
        return next;
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public void printHistory() {
        System.out.println("\n📜 Undo History (" + undoStack.size() + " snapshots):");
        undoStack.forEach(m -> System.out.println("   " + m));
        System.out.println("📜 Redo History (" + redoStack.size() + " snapshots):");
        redoStack.forEach(m -> System.out.println("   " + m));
        System.out.println();
    }
}