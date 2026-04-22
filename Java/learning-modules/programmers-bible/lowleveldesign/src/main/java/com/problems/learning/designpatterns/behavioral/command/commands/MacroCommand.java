package com.problems.learning.designpatterns.behavioral.command.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * PROBLEM SOLVED:
 * A macro groups multiple commands into one — "Good Morning" mode
 * might turn on lights, set temperature, unlock the door, and play music.
 * <p>
 * Because MacroCommand itself implements Command, it is treated
 * identically to a single command by the controller — it can be
 * executed, undone, scheduled, or logged without any special handling.
 * <p>
 * This is the Composite + Command combination: a command made of commands.
 */
public class MacroCommand implements Command {

    private final String name;
    private final List<Command> commands = new ArrayList<>();

    public MacroCommand(String name) {
        this.name = name;
    }

    public MacroCommand add(Command command) {
        commands.add(command);
        return this;   // fluent
    }

    @Override
    public void execute() {
        System.out.println("▶️  Executing macro: [" + name + "]");
        commands.forEach(Command::execute);
    }

    @Override
    public void undo() {
        // Undo in REVERSE order — last command undone first
        System.out.println("⏪ Undoing macro: [" + name + "]");
        for (int i = commands.size() - 1; i >= 0; i--) {
            commands.get(i).undo();
        }
    }

    @Override
    public String getDescription() {
        return "Macro[" + name + "] (" + commands.size() + " commands)";
    }
}