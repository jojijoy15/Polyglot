package com.problems.learning.designpatterns.behavioral.interpreter.rule;

import com.problems.learning.designpatterns.behavioral.interpreter.grammar.Context;
import com.problems.learning.designpatterns.behavioral.interpreter.grammar.Expression;

/**
 * PROBLEM SOLVED:
 * A Rule wraps an Expression with a name, description, and severity.
 * Rules can be stored in a database as plain strings, loaded at startup,
 * and evaluated against any applicant context.
 * <p>
 * The rule string is the single source of truth —
 * no Java recompilation needed when eligibility criteria change.
 */
public class Rule {

    public enum Severity {HARD_BLOCK, SOFT_BLOCK, ADVISORY}

    private final String name;
    private final String ruleString;
    private final String failMessage;
    private final Severity severity;
    private final Expression expression;

    public Rule(String name, String ruleString, String failMessage,
                Severity severity, RuleParser parser) {
        this.name = name;
        this.ruleString = ruleString;
        this.failMessage = failMessage;
        this.severity = severity;
        this.expression = parser.parse(ruleString);   // parse once, evaluate many times
    }

    public boolean evaluate(Context context) {
        return expression.interpret(context);
    }

    public String getName() {
        return name;
    }

    public String getRuleString() {
        return ruleString;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public Severity getSeverity() {
        return severity;
    }

    public Expression getExpression() {
        return expression;
    }
}