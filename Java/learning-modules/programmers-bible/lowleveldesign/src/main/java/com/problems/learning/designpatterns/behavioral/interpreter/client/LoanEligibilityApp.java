package com.problems.learning.designpatterns.behavioral.interpreter.client;

import com.problems.learning.designpatterns.behavioral.interpreter.engine.LoanEligibilityEngine;
import com.problems.learning.designpatterns.behavioral.interpreter.grammar.Context;
import com.problems.learning.designpatterns.behavioral.interpreter.grammar.Expression;
import com.problems.learning.designpatterns.behavioral.interpreter.rule.Rule;
import com.problems.learning.designpatterns.behavioral.interpreter.rule.RuleParser;

public class LoanEligibilityApp {
    public static void main(String[] args) {

        // ── Build the rule engine ─────────────────────────────────────
        LoanEligibilityEngine engine = new LoanEligibilityEngine()

                // Hard blocks — any failure = immediate rejection
                .addRule(
                        "Age Check",
                        "age >= 21 AND age <= 65",
                        "Applicant must be between 21 and 65 years old",
                        Rule.Severity.HARD_BLOCK)

                .addRule(
                        "Blacklist Check",
                        "NOT blacklisted",
                        "Applicant is on the blacklist",
                        Rule.Severity.HARD_BLOCK)

                .addRule(
                        "Employment Check",
                        "employmentStatus == EMPLOYED AND employmentYears >= 2",
                        "Must be employed for at least 2 years",
                        Rule.Severity.HARD_BLOCK)

                .addRule(
                        "Income Check",
                        "annualSalary >= 300000",
                        "Minimum annual salary of ₹3,00,000 required",
                        Rule.Severity.HARD_BLOCK)

                // Credit qualification — score OR collateral OR guarantor
                .addRule(
                        "Credit Worthiness",
                        "(creditScore >= 700) OR (hasCollateral == true) OR (guarantorPresent == true)",
                        "Needs credit score ≥ 700, or collateral, or a guarantor",
                        Rule.Severity.HARD_BLOCK)

                // Soft blocks — advisory only, do not reject
                .addRule(
                        "Debt-to-Income Ratio",
                        "existingEMI <= 400000",
                        "Existing EMIs are high — consider reducing before applying",
                        Rule.Severity.SOFT_BLOCK)

                .addRule(
                        "Premium Tier",
                        "annualSalary >= 1000000 AND creditScore >= 750",
                        "Not eligible for premium rates",
                        Rule.Severity.ADVISORY);

        // ── Scenario 1: Strong applicant ─────────────────────────────
        Context alice = new Context();
        alice.set("age", 35);
        alice.set("blacklisted", false);
        alice.set("employmentStatus", "EMPLOYED");
        alice.set("employmentYears", 8);
        alice.set("annualSalary", 1200000.0);
        alice.set("creditScore", 780.0);
        alice.set("hasCollateral", true);
        alice.set("guarantorPresent", false);
        alice.set("existingEMI", 150000.0);
        engine.evaluate("Alice (Strong Profile)", alice);

        // ── Scenario 2: Blacklisted applicant ────────────────────────
        Context bob = new Context();
        bob.set("age", 28);
        bob.set("blacklisted", true);     // ← HARD BLOCK
        bob.set("employmentStatus", "EMPLOYED");
        bob.set("employmentYears", 3);
        bob.set("annualSalary", 600000.0);
        bob.set("creditScore", 720.0);
        bob.set("hasCollateral", false);
        bob.set("guarantorPresent", false);
        bob.set("existingEMI", 100000.0);
        engine.evaluate("Bob (Blacklisted)", bob);

        // ── Scenario 3: Low credit score but has collateral ──────────
        Context carol = new Context();
        carol.set("age", 42);
        carol.set("blacklisted", false);
        carol.set("employmentStatus", "EMPLOYED");
        carol.set("employmentYears", 5);
        carol.set("annualSalary", 480000.0);
        carol.set("creditScore", 620.0);   // ← below 700
        carol.set("hasCollateral", true);    // ← but has collateral ✅
        carol.set("guarantorPresent", false);
        carol.set("existingEMI", 90000.0);
        engine.evaluate("Carol (Low Score but Collateral)", carol);

        // ── Scenario 4: Young self-employed applicant ─────────────────
        Context dave = new Context();
        dave.set("age", 19);        // ← HARD BLOCK: age < 21
        dave.set("blacklisted", false);
        dave.set("employmentStatus", "SELF_EMPLOYED");
        dave.set("employmentYears", 1);         // ← HARD BLOCK: < 2 years
        dave.set("annualSalary", 250000.0);  // ← HARD BLOCK: < 3,00,000
        dave.set("creditScore", 580.0);
        dave.set("hasCollateral", false);
        dave.set("guarantorPresent", false);
        dave.set("existingEMI", 50000.0);
        engine.evaluate("Dave (Multiple Failures)", dave);

        // ── Scenario 5: Dynamically change a rule at runtime ─────────
        System.out.println("\n========== Scenario 5: Ad-hoc Rule Evaluation ==========");

        RuleParser parser = new RuleParser();
        String customRule = "(annualSalary >= 500000 AND creditScore >= 680) "
                + "OR (hasCollateral == true AND annualSalary >= 300000)";

        Expression expr = parser.parse(customRule);
        System.out.println("Rule   : " + customRule);
        System.out.println("Tree   : " + expr.describe());
        System.out.println("Alice  : " + expr.interpret(alice));   // true
        System.out.println("Dave   : " + expr.interpret(dave));    // false
    }
}