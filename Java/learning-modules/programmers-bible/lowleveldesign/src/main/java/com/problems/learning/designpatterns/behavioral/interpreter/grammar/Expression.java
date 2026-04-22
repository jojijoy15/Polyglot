package com.problems.learning.designpatterns.behavioral.interpreter.grammar;

/**
 * PROBLEM SOLVED:
 * Without Interpreter, eligibility logic lives in hardcoded if-else chains:
 * <p>
 * if (applicant.getAge() > 21
 * && applicant.getSalary() > 50000
 * && (applicant.getCreditScore() > 700 || applicant.hasCollateral())
 * && !applicant.isBlacklisted()) { approve(); }
 * <p>
 * Problems:
 * 1. Adding/changing rules requires code changes and redeployment
 * 2. Business analysts cannot write or read the rules
 * 3. Different loan products need different rule sets — combinatorial explosion
 * 4. Rules cannot be stored in a database and loaded dynamically
 * <p>
 * Interpreter solves this by representing each rule as an expression object.
 * Rules become data — parsed, stored, composed, and evaluated at runtime.
 */
public interface Expression {

    /**
     * Evaluate this expression against the given context.
     * Terminal expressions read variables from the context.
     * Non-terminal expressions combine child expressions recursively.
     */
    boolean interpret(Context context);

    /**
     * Human-readable representation of the expression tree.
     * Used for audit logs and debugging.
     */
    String describe();
}