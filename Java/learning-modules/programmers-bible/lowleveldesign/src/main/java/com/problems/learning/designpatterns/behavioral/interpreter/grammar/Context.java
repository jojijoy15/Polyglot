package com.problems.learning.designpatterns.behavioral.interpreter.grammar;

import java.util.HashMap;
import java.util.Map;

/**
 * PROBLEM SOLVED:
 * The Context holds all variable values for a given loan applicant.
 * Expressions don't reach into the applicant object directly —
 * they only query the context by variable name.
 * <p>
 * This decouples the expression tree from any specific domain object.
 * The same rule engine evaluates applicants, businesses, or any
 * entity simply by loading different values into the context.
 */
public class Context {

    private final Map<String, Object> variables = new HashMap<>();

    public void set(String name, Object value) {
        variables.put(name, value);
    }

    public Object get(String name) {
        if (!variables.containsKey(name)) {
            throw new IllegalArgumentException("Unknown variable: '" + name
                    + "'. Available: " + variables.keySet());
        }
        return variables.get(name);
    }

    public double getNumeric(String name) {
        Object val = get(name);
        if (val instanceof Number n) return n.doubleValue();
        throw new IllegalArgumentException("Variable '" + name + "' is not numeric: " + val);
    }

    public boolean getBoolean(String name) {
        Object val = get(name);
        if (val instanceof Boolean b) return b;
        throw new IllegalArgumentException("Variable '" + name + "' is not boolean: " + val);
    }

    public String getString(String name) {
        return get(name).toString();
    }

    @Override
    public String toString() {
        return "Context" + variables;
    }
}