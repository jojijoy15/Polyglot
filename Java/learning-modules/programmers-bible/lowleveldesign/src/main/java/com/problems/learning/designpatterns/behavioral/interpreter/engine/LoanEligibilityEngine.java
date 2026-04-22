package com.problems.learning.designpatterns.behavioral.interpreter.engine;

import com.problems.learning.designpatterns.behavioral.interpreter.grammar.Context;
import com.problems.learning.designpatterns.behavioral.interpreter.rule.Rule;
import com.problems.learning.designpatterns.behavioral.interpreter.rule.RuleParser;

import java.util.ArrayList;
import java.util.List;

/**
 * PROBLEM SOLVED:
 * The engine holds a registry of named rules and evaluates
 * an applicant context against all of them.
 * <p>
 * Adding a new rule = one method call.
 * Changing a rule = update the string in the database.
 * Zero Java code changes for business rule updates.
 */
public class LoanEligibilityEngine {

    private final List<Rule> rules = new ArrayList<>();
    private final RuleParser parser = new RuleParser();

    public LoanEligibilityEngine addRule(String name, String ruleString,
                                         String failMessage, Rule.Severity severity) {
        rules.add(new Rule(name, ruleString, failMessage, severity, parser));
        return this;   // fluent
    }

    public EvaluationResult evaluate(String applicantName, Context context) {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.printf("║  Evaluating: %-35s ║%n", applicantName);
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println("   Context: " + context);
        System.out.println();

        EvaluationResult result = new EvaluationResult(applicantName);

        for (Rule rule : rules) {
            boolean passed = rule.evaluate(context);
            result.addRuleResult(rule, passed);

            System.out.printf("   [%-9s] %-28s → %s%n",
                    rule.getSeverity().name().replace("_", " "),
                    rule.getName(),
                    passed ? "✅ PASS" : "❌ FAIL — " + rule.getFailMessage());

            // Print expression tree for failed rules (audit trail)
            if (!passed) {
                System.out.println("              Rule: " + rule.getExpression().describe());
            }
        }

        result.printSummary();
        return result;
    }
}