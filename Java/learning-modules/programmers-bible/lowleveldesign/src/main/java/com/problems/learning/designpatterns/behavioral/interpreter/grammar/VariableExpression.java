package com.problems.learning.designpatterns.behavioral.interpreter.grammar;

/**
 * Terminal expression for standalone boolean variables.
 * Allows rules like "NOT blacklisted" where "blacklisted"
 * is a boolean variable in the context — no comparison operator needed.
 */
public class VariableExpression implements Expression {

    private final String variableName;

    public VariableExpression(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public boolean interpret(Context context) {
        return context.getBoolean(variableName);
    }

    @Override
    public String describe() {
        return variableName;
    }
}

