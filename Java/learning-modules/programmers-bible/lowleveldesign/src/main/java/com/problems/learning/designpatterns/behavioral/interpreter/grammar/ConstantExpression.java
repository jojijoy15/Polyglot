package com.problems.learning.designpatterns.behavioral.interpreter.grammar;

/**
 * PROBLEM SOLVED:
 * Useful for rules that unconditionally pass or fail a branch —
 * e.g. a feature flag that temporarily disables a check:
 * <p>
 * TRUE AND salary > 50000   ← skip age check during a promotion
 */
public class ConstantExpression implements Expression {

    private final boolean value;

    public ConstantExpression(boolean value) {
        this.value = value;
    }

    @Override
    public boolean interpret(Context context) {
        return value;
    }

    @Override
    public String describe() {
        return value ? "TRUE" : "FALSE";
    }
}