package com.problems.learning.designpatterns.behavioral.interpreter.engine;

import com.problems.learning.designpatterns.behavioral.interpreter.rule.Rule;

import java.util.ArrayList;
import java.util.List;

public class EvaluationResult {

    private final String applicantName;
    private final List<RuleResult> results = new ArrayList<>();
    private boolean eligible;

    public EvaluationResult(String applicantName) {
        this.applicantName = applicantName;
        this.eligible = true;
    }

    public void addRuleResult(Rule rule, boolean passed) {
        results.add(new RuleResult(rule, passed));
        // A single HARD_BLOCK failure makes the applicant ineligible
        if (!passed && rule.getSeverity() == Rule.Severity.HARD_BLOCK) {
            eligible = false;
        }
    }

    public void printSummary() {
        long passed = results.stream().filter(r -> r.passed).count();
        long failed = results.size() - passed;

        System.out.println();
        System.out.println("   ─────────────────────────────────────────────────");
        System.out.printf("   Rules passed : %d / %d%n", passed, results.size());
        System.out.printf("   Rules failed : %d%n", failed);
        System.out.printf("   Decision     : %s%n",
                eligible ? "✅ APPROVED — Eligible for loan"
                        : "❌ REJECTED — Does not meet eligibility criteria");
        System.out.println("   ─────────────────────────────────────────────────");
    }

    public boolean isEligible() {
        return eligible;
    }

    private record RuleResult(Rule rule, boolean passed) {
    }
}