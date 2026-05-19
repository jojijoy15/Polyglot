package com.problems.learning.ds.graph;

import java.util.*;

public class EmployeeImportance {

    /*
     * Given employees with (id, name, managerId, importance), find total importance
     * for each employee = their own importance + sum of all subordinates' importance.
     *
     * Data Structures:
     *   1. HashMap<id, Employee>        → O(1) lookup by id
     *   2. HashMap<managerId, List<id>> → adjacency list (parent → children)
     *
     * Approach: DFS with memoization (bottom-up caching)
     *   - For each employee, recursively sum children's total importance
     *   - Cache results so each node is visited only once
     *   - Time: O(n), Space: O(n)
     *
     * Example:
     *         Alice(5)
     *        /      \
     *     Bob(3)   Charlie(4)
     *    /    \        \
     * Dave(1) Eve(2)  Frank(6)
     *
     * totalImportance(Dave)    = 1
     * totalImportance(Eve)     = 2
     * totalImportance(Frank)   = 6
     * totalImportance(Bob)     = 3 + 1 + 2 = 6
     * totalImportance(Charlie) = 4 + 6     = 10
     * totalImportance(Alice)   = 5 + 6 + 10 = 21
     */

    public static class Employee {
        int id;
        String name;
        int managerId; // -1 if no manager (root/CEO)
        int importance;

        public Employee(int id, String name, int managerId, int importance) {
            this.id = id;
            this.name = name;
            this.managerId = managerId;
            this.importance = importance;
        }
    }

    /**
     * Returns a map of employeeId → total importance (self + all subordinates).
     */
    public Map<Integer, Integer> calculateTotalImportance(List<Employee> employees) {
        // Build lookup and adjacency list
        Map<Integer, Employee> employeeMap = new HashMap<>();
        Map<Integer, List<Integer>> subordinates = new HashMap<>();

        for (Employee emp : employees) {
            employeeMap.put(emp.id, emp);
            subordinates.putIfAbsent(emp.id, new ArrayList<>());
            if (emp.managerId != -1) {
                subordinates.computeIfAbsent(emp.managerId, k -> new ArrayList<>()).add(emp.id);
            }
        }

        // DFS with memoization
        Map<Integer, Integer> memo = new HashMap<>();
        for (Employee emp : employees) {
            dfs(emp.id, employeeMap, subordinates, memo);
        }

        return memo;
    }

    private int dfs(int empId, Map<Integer, Employee> employeeMap,
                    Map<Integer, List<Integer>> subordinates, Map<Integer, Integer> memo) {
        if (memo.containsKey(empId)) return memo.get(empId);

        int total = employeeMap.get(empId).importance;
        for (int childId : subordinates.getOrDefault(empId, Collections.emptyList())) {
            total += dfs(childId, employeeMap, subordinates, memo);
        }

        memo.put(empId, total);
        return total;
    }

    /**
     * Get total importance for a single employee by id.
     */
    public int getImportance(List<Employee> employees, int targetId) {
        return calculateTotalImportance(employees).getOrDefault(targetId, 0);
    }
}

