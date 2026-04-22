package com.problems.learning.designpatterns.behavioral.interpreter.grammar;

/**
 * PROBLEM SOLVED:
 * ComparisonExpression handles all atomic comparisons:
 * age > 21, salary >= 50000, creditScore == 750, status != "DEFAULTED"
 * <p>
 * It is the leaf node of the expression tree — reads one variable
 * from the context and compares it against a literal value.
 * <p>
 * Adding a new operator (e.g. "CONTAINS") only requires adding
 * one case here — zero changes to the tree structure or parser.
 */
public class ComparisonExpression implements Expression {

    private final String variableName;
    private final String operator;       // >, <, >=, <=, ==, !=
    private final String value;          // right-hand side literal

    public ComparisonExpression(String variableName, String operator, String value) {
        this.variableName = variableName.trim();
        this.operator = operator.trim();
        this.value = value.trim();
    }

    @Override
    public boolean interpret(Context context) {
        Object contextValue = context.get(variableName);

        // Numeric comparison
        if (contextValue instanceof Number) {
            double left = ((Number) contextValue).doubleValue();
            double right = Double.parseDouble(value);
            return switch (operator) {
                case ">" -> left > right;
                case "<" -> left < right;
                case ">=" -> left >= right;
                case "<=" -> left <= right;
                case "==" -> left == right;
                case "!=" -> left != right;
                default -> throw new IllegalArgumentException("Unknown operator: " + operator);
            };
        }

        // Boolean comparison
        if (contextValue instanceof Boolean) {
            boolean left = (Boolean) contextValue;
            boolean right = Boolean.parseBoolean(value);
            return switch (operator) {
                case "==" -> left == right;
                case "!=" -> left != right;
                default -> throw new IllegalArgumentException(
                        "Boolean only supports == and !=, got: " + operator);
            };
        }

        // String comparison
        String left = contextValue.toString();
        return switch (operator) {
            case "==" -> left.equalsIgnoreCase(value);
            case "!=" -> !left.equalsIgnoreCase(value);
            default -> throw new IllegalArgumentException(
                    "String only supports == and !=, got: " + operator);
        };
    }

    @Override
    public String describe() {
        return variableName + " " + operator + " " + value;
    }
}