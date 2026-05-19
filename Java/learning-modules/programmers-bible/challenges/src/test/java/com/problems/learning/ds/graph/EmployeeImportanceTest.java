package com.problems.learning.ds.graph;

import com.problems.learning.ds.graph.EmployeeImportance.Employee;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeImportanceTest {

    private final EmployeeImportance solver = new EmployeeImportance();

    /*
     *         Alice(5)
     *        /      \
     *     Bob(3)   Charlie(4)
     *    /    \        \
     * Dave(1) Eve(2)  Frank(6)
     */
    private List<Employee> buildTeam() {
        return List.of(
                new Employee(1, "Alice", -1, 5),
                new Employee(2, "Bob", 1, 3),
                new Employee(3, "Charlie", 1, 4),
                new Employee(4, "Dave", 2, 1),
                new Employee(5, "Eve", 2, 2),
                new Employee(6, "Frank", 3, 6)
        );
    }

    @Test
    void allEmployeeTotalImportance() {
        Map<Integer, Integer> result = solver.calculateTotalImportance(buildTeam());

        assertThat(result).containsEntry(1, 21); // Alice: 5 + 6 + 10
        assertThat(result).containsEntry(2, 6);  // Bob: 3 + 1 + 2
        assertThat(result).containsEntry(3, 10); // Charlie: 4 + 6
        assertThat(result).containsEntry(4, 1);  // Dave: 1
        assertThat(result).containsEntry(5, 2);  // Eve: 2
        assertThat(result).containsEntry(6, 6);  // Frank: 6
    }

    @Test
    void singleEmployeeImportance_root() {
        assertThat(solver.getImportance(buildTeam(), 1)).isEqualTo(21);
    }

    @Test
    void singleEmployeeImportance_midLevel() {
        assertThat(solver.getImportance(buildTeam(), 2)).isEqualTo(6);
    }

    @Test
    void singleEmployeeImportance_leaf() {
        assertThat(solver.getImportance(buildTeam(), 4)).isEqualTo(1);
    }

    @Test
    void singleEmployee_noSubordinates() {
        List<Employee> solo = List.of(new Employee(1, "Solo", -1, 10));
        assertThat(solver.getImportance(solo, 1)).isEqualTo(10);
    }

    @Test
    void deepHierarchy() {
        // A → B → C → D (chain)
        List<Employee> chain = List.of(
                new Employee(1, "A", -1, 1),
                new Employee(2, "B", 1, 2),
                new Employee(3, "C", 2, 3),
                new Employee(4, "D", 3, 4)
        );
        Map<Integer, Integer> result = solver.calculateTotalImportance(chain);
        assertThat(result).containsEntry(1, 10); // 1+2+3+4
        assertThat(result).containsEntry(2, 9);  // 2+3+4
        assertThat(result).containsEntry(3, 7);  // 3+4
        assertThat(result).containsEntry(4, 4);  // 4
    }
}

