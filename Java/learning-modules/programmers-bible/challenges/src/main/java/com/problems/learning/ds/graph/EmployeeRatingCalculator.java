package com.problems.learning.ds.graph;

import lombok.Getter;
import lombok.ToString;

import java.util.*;
import java.util.stream.*;

public class EmployeeRatingCalculator {


    @Getter
    @ToString
    public static class Employee {
        private final int id;
        private final Integer managerId;   // null for top-level (CEO)
        private final int rating;

        public Employee(int id, Integer managerId, int rating) {
            this.id        = id;
            this.managerId = managerId;
            this.rating    = rating;
        }

    }

    // ── Step 1: Build manager → direct reports map ────────────────
    private static Map<Integer, List<Employee>> buildReportingMap(List<Employee> employees) {
        return employees.stream()
                .filter(e -> e.getManagerId() != null)  // exclude root
                .collect(Collectors.groupingBy(Employee::getManagerId));
    }

    // ── Step 2: DFS to accumulate subtree rating ──────────────────
    private static int dfs(int managerId,
                           Map<Integer, List<Employee>> reportingMap,
                           Map<Integer, Integer> overallRatings) {

        List<Employee> directReports = reportingMap
                .getOrDefault(managerId, Collections.emptyList());

        // Sum own rating (already stored) + all reportees' subtrees
        int reporteesRating = directReports.stream()
                .mapToInt(e -> dfs(e.getId(), reportingMap, overallRatings))
                .sum();

        // Update overall rating = own rating + all indirect ratings
        overallRatings.merge(managerId, reporteesRating, Integer::sum);

        return overallRatings.get(managerId);  // return subtree total to parent
    }

    // ── Step 3: Main calculation entry point ──────────────────────
    public static Map<Integer, Integer> calculateOverallRatings(List<Employee> employees) {

        // Map each employee's own rating as the base
        Map<Integer, Integer> overallRatings = employees.stream()
                .collect(Collectors.toMap(
                        Employee::getId,
                        Employee::getRating
                ));

        // Build adjacency list: managerId → [direct reports]
        Map<Integer, List<Employee>> reportingMap = buildReportingMap(employees);

        // Find root employees (no manager) and kick off DFS from each
        employees.stream()
                .filter(e -> e.getManagerId() == null)
                .forEach(root -> dfs(root.getId(), reportingMap, overallRatings));

        return overallRatings;
    }

    // ── Pretty print result ───────────────────────────────────────
    public static void printResults(List<Employee> employees,
                                    Map<Integer, Integer> overallRatings) {

        Map<Integer, Integer> ownRatings = employees.stream()
                .collect(Collectors.toMap(Employee::getId, Employee::getRating));

        System.out.println("╔══════╦════════════╦════════════╦════════════════╗");
        System.out.println("║  ID  ║ Manager ID ║ Own Rating ║ Overall Rating ║");
        System.out.println("╠══════╬════════════╬════════════╬════════════════╣");

        employees.stream()
                .sorted(Comparator.comparingInt(Employee::getId))
                .forEach(e -> System.out.printf(
                        "║  %-3d ║     %-6s ║     %-6d ║      %-9d ║%n",
                        e.getId(),
                        e.getManagerId() == null ? "None" : e.getManagerId(),
                        ownRatings.get(e.getId()),
                        overallRatings.get(e.getId())
                ));

        System.out.println("╚══════╩════════════╩════════════╩════════════════╝");
    }
}