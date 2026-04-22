package com.problems.learning.designpatterns.behavioral.memento.client;

import com.problems.learning.designpatterns.behavioral.memento.editor.EditorState;
import com.problems.learning.designpatterns.behavioral.memento.editor.HistoryManager;

public class Notepad {
    public static void main(String[] args) {

        EditorState editor = new EditorState("Untitled.java");
        HistoryManager history = new HistoryManager();

        // ── Step 1: Type some code ──────────────────────────────────────
        history.push(editor.save());               // snapshot 1 — empty state
        editor.type("public class Hello {");
        history.push(editor.save());               // snapshot 2

        editor.type("\n    public static void main");
        history.push(editor.save());               // snapshot 3

        editor.type("(String[] args) {");
        history.push(editor.save());               // snapshot 4

        editor.printState();

        // ── Step 2: Rename the file ─────────────────────────────────────
        editor.rename("Hello.java");
        history.push(editor.save());               // snapshot 5

        // ── Step 3: Undo rename ─────────────────────────────────────────
        System.out.println("\n===== UNDO rename =====");
        editor.restore(history.undo(editor.save()));
        editor.printState();

        // ── Step 4: Undo twice more ─────────────────────────────────────
        System.out.println("===== UNDO type (String[] args) =====");
        editor.restore(history.undo(editor.save()));
        editor.printState();

        System.out.println("===== UNDO type main =====");
        editor.restore(history.undo(editor.save()));
        editor.printState();

        // ── Step 5: Redo once ───────────────────────────────────────────
        System.out.println("===== REDO =====");
        editor.restore(history.redo(editor.save()));
        editor.printState();

        // ── Step 6: New action clears redo stack ────────────────────────
        System.out.println("===== New action — type something new =====");
        editor.type("\n    // TODO: implement");
        history.push(editor.save());               // redo stack cleared here!

        history.printHistory();
    }
}