package com.problems.learning.designpatterns.behavioral.interpreter.grammar;

/**
 * PROBLEM SOLVED:
 * NOT inverts any child expression — terminal or composite:
 * NOT blacklisted
 * NOT (age < 18 OR income < 10000)
 * <p>
 * Wrapping any expression in NOT requires zero changes to the
 * wrapped expression — pure structural composition.
 */
public class NotExpression implements Expression {

    private final Expression expression;

    public NotExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public boolean interpret(Context context) {
        return !expression.interpret(context);
    }

    @Override
    public String describe() {
        return "NOT (" + expression.describe() + ")";
    }
}