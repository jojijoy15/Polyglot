package com.problems.learning.designpatterns.behavioral.memento.editor;

/**
 * PROBLEM SOLVED:
 * The editor holds its own internal state (content, cursor, filename).
 * It needs the ability to snapshot itself and restore from a snapshot —
 * but it should NOT delegate this responsibility to some external class
 * that would require making its fields public.
 * <p>
 * The Originator is the only class that knows how to:
 * 1. Pack its state INTO a Memento (save)
 * 2. Unpack a Memento BACK into itself (restore)
 * <p>
 * The rest of the system just passes Mementos around as opaque objects.
 */
public class EditorState {

    private String content;
    private int cursorPosition;
    private String fileName;

    public EditorState(String fileName) {
        this.fileName = fileName;
        this.content = "";
        this.cursorPosition = 0;
        System.out.println("📄 Opened file: " + fileName);
    }

    // --- Normal editor operations ---

    public void type(String text) {
        // Insert text at cursor position
        content = content.substring(0, cursorPosition)
                + text
                + content.substring(cursorPosition);
        cursorPosition += text.length();
        System.out.println("⌨️  Typed     : \"" + text + "\"");
    }

    public void delete(int characters) {
        // Delete characters before the cursor (like Backspace)
        if (cursorPosition == 0) {
            System.out.println("⚠️  Nothing to delete");
            return;
        }
        int deleteFrom = Math.max(0, cursorPosition - characters);
        String deleted = content.substring(deleteFrom, cursorPosition);
        content = content.substring(0, deleteFrom) + content.substring(cursorPosition);
        cursorPosition = deleteFrom;
        System.out.println("🗑️  Deleted   : \"" + deleted + "\"");
    }

    public void moveCursor(int position) {
        this.cursorPosition = Math.max(0, Math.min(position, content.length()));
        System.out.println("🖱️  Cursor at : " + cursorPosition);
    }

    public void rename(String newName) {
        System.out.println("✏️  Renamed   : " + fileName + " → " + newName);
        this.fileName = newName;
    }

    // -----------------------------------------------------------
    // Memento operations — ONLY this class packs/unpacks state
    // -----------------------------------------------------------

    /**
     * PROBLEM SOLVED:
     * Instead of exposing getContent(), getCursor() etc. to a history
     * manager and letting IT construct a snapshot, the editor packs
     * its own state here. Encapsulation stays intact.
     */
    public EditorMemento save() {
        EditorMemento snapshot = new EditorMemento(content, cursorPosition, fileName);
        System.out.println("💾 Saved      : " + snapshot);
        return snapshot;
    }

    /**
     * PROBLEM SOLVED:
     * Restoring is symmetric — only the Originator knows which fields
     * to pull from the Memento. The Caretaker (HistoryManager) just
     * hands back an opaque Memento object without knowing what's inside.
     */
    public void restore(EditorMemento memento) {
        this.content = memento.getContent();
        this.cursorPosition = memento.getCursorPosition();
        this.fileName = memento.getFileName();
        System.out.println("⏪ Restored   : " + memento);
    }

    // --- Display ---
    public void printState() {
        System.out.println("\n📋 Current State ──────────────────────────");
        System.out.println("   File    : " + fileName);
        System.out.println("   Content : \"" + content + "\"");
        System.out.println("   Cursor  : " + cursorPosition);
        System.out.println("───────────────────────────────────────────\n");
    }
}