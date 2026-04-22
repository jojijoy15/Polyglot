package com.problems.learning.designpatterns.behavioral.interpreter.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PROBLEM SOLVED:
 * AND combines N sub-expressions — ALL must be true.
 * It is non-terminal: it holds references to child expressions
 * which themselves may be AND, OR, NOT, or Comparison nodes.
 * <p>
 * This recursive structure is the core of Interpreter —
 * complex rules are composed from simple building blocks.
 */
public class AndExpression implements Expression {

    private final List<Expression> expressions;

    public AndExpression(List<Expression> expressions) {
        this.expressions = new ArrayList<>(expressions);
    }

    public AndExpression(Expression left, Expression right) {
        this.expressions = List.of(left, right);
    }

    @Override
    public boolean interpret(Context context) {
        // Short-circuit evaluation — stop at first false
        for (Expression expr : expressions) {
            if (!expr.interpret(context)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String describe() {
        return expressions.stream()
                .map(e -> "(" + e.describe() + ")")
                .collect(Collectors.joining(" AND "));
    }
}