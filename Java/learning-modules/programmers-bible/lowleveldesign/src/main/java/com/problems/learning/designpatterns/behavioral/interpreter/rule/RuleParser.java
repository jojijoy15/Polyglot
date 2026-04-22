package com.problems.learning.designpatterns.behavioral.interpreter.rule;

import com.problems.learning.designpatterns.behavioral.interpreter.grammar.*;

import java.util.ArrayList;
import java.util.List;

/**
 * PROBLEM SOLVED:
 * Rules arrive as human-readable strings from a database or config file.
 * The parser converts them into an expression tree that can be evaluated
 * against any context.
 * <p>
 * Without a parser, expression trees would be hardcoded in Java:
 * new AndExpression(
 * new ComparisonExpression("age", ">", "21"),
 * new ComparisonExpression("salary", ">", "50000")
 * )
 * <p>
 * With the parser, the same rule comes from a config string:
 * "age > 21 AND salary > 50000"
 * <p>
 * Business analysts write rules — no Java code required.
 */
public class RuleParser {

    private static final String[] COMPARISON_OPS = {">=", "<=", "!=", "==", ">", "<"};

    /**
     * Main entry point — parses a full rule string into an expression tree.
     * <p>
     * Supported grammar:
     * rule        ::= or_expr
     * or_expr     ::= and_expr ("OR" and_expr)*
     * and_expr    ::= primary ("AND" primary)*
     * primary     ::= "NOT" primary | "(" rule ")" | comparison
     * comparison  ::= variable operator literal
     * operator    ::= ">" | "<" | ">=" | "<=" | "==" | "!="
     */
    public Expression parse(String rule) {
        List<String> tokens = tokenize(rule.trim());
        TokenStream stream = new TokenStream(tokens);
        return parseOr(stream);
    }

    // ── Recursive descent parser ──────────────────────────────────────

    private Expression parseOr(TokenStream stream) {
        List<Expression> operands = new ArrayList<>();
        operands.add(parseAnd(stream));

        while (stream.hasNext() && stream.peek().equalsIgnoreCase("OR")) {
            stream.consume("OR");
            operands.add(parseAnd(stream));
        }

        return operands.size() == 1 ? operands.get(0) : new OrExpression(operands);
    }

    private Expression parseAnd(TokenStream stream) {
        List<Expression> operands = new ArrayList<>();
        operands.add(parsePrimary(stream));

        while (stream.hasNext() && stream.peek().equalsIgnoreCase("AND")) {
            stream.consume("AND");
            operands.add(parsePrimary(stream));
        }

        return operands.size() == 1 ? operands.get(0) : new AndExpression(operands);
    }

    private Expression parsePrimary(TokenStream stream) {
        String token = stream.peek();

        // NOT expression
        if (token.equalsIgnoreCase("NOT")) {
            stream.consume("NOT");
            return new NotExpression(parsePrimary(stream));
        }

        // Grouped expression in parentheses
        if (token.equals("(")) {
            stream.consume("(");
            Expression inner = parseOr(stream);
            stream.consume(")");
            return inner;
        }

        // Constant TRUE / FALSE
        if (token.equalsIgnoreCase("TRUE")) {
            stream.next();
            return new ConstantExpression(true);
        }
        if (token.equalsIgnoreCase("FALSE")) {
            stream.next();
            return new ConstantExpression(false);
        }

        // Standalone boolean variable: e.g. "blacklisted" in "NOT blacklisted"
        // Detected when the next token after the variable is end-of-stream,
        // a closing ")", or a logical keyword (AND / OR).
        if (!stream.hasNextAfter(1) || isEndOfExpression(stream.peekAt(1))) {
            return new VariableExpression(stream.next());
        }

        // Comparison: variable operator value
        return parseComparison(stream);
    }

    private boolean isEndOfExpression(String token) {
        return token.equals(")") || token.equalsIgnoreCase("AND") || token.equalsIgnoreCase("OR");
    }

    private Expression parseComparison(TokenStream stream) {
        if (stream.remaining() < 3) {
            throw new IllegalArgumentException(
                    "Incomplete comparison — expected 'variable operator value' but only "
                            + stream.remaining() + " token(s) remain");
        }
        String variable = stream.next();
        String operator = stream.next();
        String value = stream.next();

        // Validate operator
        boolean validOp = false;
        for (String op : COMPARISON_OPS) {
            if (op.equals(operator)) {
                validOp = true;
                break;
            }
        }
        if (!validOp) {
            throw new IllegalArgumentException("Invalid operator: '" + operator
                    + "' near variable '" + variable + "'");
        }

        return new ComparisonExpression(variable, operator, value);
    }

    // ── Tokenizer ─────────────────────────────────────────────────────

    /**
     * Splits the rule string into tokens, handling:
     * - Multi-character operators (>=, <=, !=, ==)
     * - Parentheses as separate tokens
     * - Quoted string values
     * - Keywords (AND, OR, NOT)
     */
    private List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        int i = 0;

        while (i < input.length()) {
            char c = input.charAt(i);

            // Skip whitespace
            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            // Parentheses
            if (c == '(' || c == ')') {
                tokens.add(String.valueOf(c));
                i++;
                continue;
            }

            // Quoted string: "DEFAULTED"
            if (c == '"' || c == '\'') {
                char quote = c;
                int start = ++i;
                while (i < input.length() && input.charAt(i) != quote) i++;
                tokens.add(input.substring(start, i++));
                continue;
            }

            // Multi-char operators: >=, <=, !=, ==
            if (i + 1 < input.length()) {
                String twoChar = input.substring(i, i + 2);
                if (twoChar.equals(">=") || twoChar.equals("<=")
                        || twoChar.equals("!=") || twoChar.equals("==")) {
                    tokens.add(twoChar);
                    i += 2;
                    continue;
                }
            }

            // Single-char operators: >, <, !, =
            if (c == '>' || c == '<' || c == '!' || c == '=') {
                tokens.add(String.valueOf(c));
                i++;
                continue;
            }

            // Words: variable names, keywords (AND, OR, NOT), numeric/boolean literals
            int start = i;
            while (i < input.length()
                    && !Character.isWhitespace(input.charAt(i))
                    && input.charAt(i) != '('
                    && input.charAt(i) != ')'
                    && input.charAt(i) != '>'
                    && input.charAt(i) != '<'
                    && input.charAt(i) != '!'
                    && input.charAt(i) != '=') {
                i++;
            }
            if (i > start) tokens.add(input.substring(start, i));
        }

        return tokens;
    }

    // ── Token Stream Helper ───────────────────────────────────────────

    private static class TokenStream {
        private final List<String> tokens;
        private int cursor;

        TokenStream(List<String> tokens) {
            this.tokens = tokens;
            this.cursor = 0;
        }

        boolean hasNext() {
            return cursor < tokens.size();
        }

        int remaining() {
            return tokens.size() - cursor;
        }

        boolean hasNextAfter(int offset) {
            return (cursor + offset) < tokens.size();
        }

        String peekAt(int offset) {
            return tokens.get(cursor + offset);
        }

        String peek() {
            return tokens.get(cursor);
        }

        String next() {
            return tokens.get(cursor++);
        }

        void consume(String expected) {
            String actual = next();
            if (!actual.equalsIgnoreCase(expected)) {
                throw new IllegalArgumentException(
                        "Expected '" + expected + "' but got '" + actual + "'");
            }
        }
    }
}