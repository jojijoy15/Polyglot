package com.problems.learning.designpatterns.behavioral.interpreter.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PROBLEM SOLVED:
 * OR combines N sub-expressions — at least ONE must be true.
 * Used for alternative qualifications:
 * (creditScore > 700 OR hasCollateral == true OR guarantorPresent == true)
 */
public class OrExpression implements Expression {

    private final List<Expression> expressions;

    public OrExpression(List<Expression> expressions) {
        this.expressions = new ArrayList<>(expressions);
    }

    public OrExpression(Expression left, Expression right) {
        this.expressions = List.of(left, right);
    }

    @Override
    public boolean interpret(Context context) {
        // Short-circuit evaluation — stop at first true
        for (Expression expr : expressions) {
            if (expr.interpret(context)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String describe() {
        return expressions.stream()
                .map(e -> "(" + e.describe() + ")")
                .collect(Collectors.joining(" OR "));
    }
}