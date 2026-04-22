package com.problems.learning.designpatterns.behavioral.command.controllers;

import com.problems.learning.designpatterns.behavioral.command.commands.Command;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * PROBLEM SOLVED:
 * The Invoker is the core value of Command pattern.
 * HomeController executes, undoes, schedules, and logs commands
 * WITHOUT knowing anything about lights, thermostats, doors, or speakers.
 * <p>
 * It only knows: Command.execute() and Command.undo()
 * <p>
 * This means:
 * - Adding a new device (e.g. SecurityCamera) = zero changes to HomeController
 * - Undo/redo works automatically for every command ever registered
 * - Audit log is built automatically — no device needs to log itself
 */
public class HomeController {

    private final Deque<Command> undoStack = new ArrayDeque<>();
    private final Deque<Command> redoStack = new ArrayDeque<>();
    private final List<String> auditLog = new ArrayList<>();
    private final List<Command> scheduled = new ArrayList<>();

    // ── Execute ───────────────────────────────────────────────────────

    public void execute(Command command) {
        System.out.println("\n⚡ EXECUTE: " + command.getDescription());
        command.execute();
        undoStack.push(command);
        redoStack.clear();                // new action clears redo history
        auditLog.add("EXECUTE → " + command.getDescription());
    }

    // ── Undo ──────────────────────────────────────────────────────────

    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("⚠️  Nothing to undo");
            return;
        }
        Command command = undoStack.pop();
        System.out.println("\n↩️  UNDO: " + command.getDescription());
        command.undo();
        redoStack.push(command);
        auditLog.add("UNDO   → " + command.getDescription());
    }

    // ── Redo ──────────────────────────────────────────────────────────

    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("⚠️  Nothing to redo");
            return;
        }
        Command command = redoStack.pop();
        System.out.println("\n↪️  REDO: " + command.getDescription());
        command.execute();
        undoStack.push(command);
        auditLog.add("REDO   → " + command.getDescription());
    }

    // ── Schedule ──────────────────────────────────────────────────────

    /**
     * PROBLEM SOLVED:
     * Scheduling is trivial because commands are objects.
     * Store them now, run them later — no special handling needed.
     */
    public void schedule(Command command) {
        scheduled.add(command);
        System.out.println("⏰ SCHEDULED: " + command.getDescription());
    }

    public void runScheduled() {
        System.out.println("\n⏰ Running " + scheduled.size() + " scheduled commands...");
        scheduled.forEach(this::execute);
        scheduled.clear();
    }

    // ── Audit Log ─────────────────────────────────────────────────────

    public void printAuditLog() {
        System.out.println("\n📋 Audit Log ─────────────────────────────");
        auditLog.forEach(entry -> System.out.println("   " + entry));
        System.out.println("──────────────────────────────────────────");
    }
}