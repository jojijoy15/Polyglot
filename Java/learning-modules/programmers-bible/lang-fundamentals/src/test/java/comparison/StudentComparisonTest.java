package comparison;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StudentComparisonTest {

    @Test
    public void testStudentComparison() {
        Student s1 = new Student("Joji", List.of(10, 12, 15), 1);
        Student s2 = new Student("Joji", List.of(10, 12, 15), 1);
        assertThat(s1.compareTo(s2)).isEqualTo(0);
    }

    @Test
    public void testStudentComparisonLessThan() {
        Student s1 = new Student("Ana", List.of(10, 12, 15), 1);
        Student s2 = new Student("Arakin", List.of(10, 12, 15), 1);
        assertThat(s1.compareTo(s2)).isLessThan(0);
    }

    @Test
    public void testStudentComparisonGreaterThan() {
        Student s1 = new Student("Zennat", List.of(10, 12, 15), 1);
        Student s2 = new Student("Arakin", List.of(10, 12, 15), 1);
        assertThat(s1.compareTo(s2)).isGreaterThan(0);
    }


    @Test
    public void testStudentComparisonMarksLessThan() {
        Student s1 = new Student("Ana", List.of(10, 10, 15), 1);
        Student s2 = new Student("Ana", List.of(10, 12, 15), 1);
        assertThat(s1.compareTo(s2)).isLessThan(0);
    }

    @Test
    public void testStudentComparisonMarksGreaterThan() {
        Student s1 = new Student("Ana", List.of(10, 22, 15), 1);
        Student s2 = new Student("Ana", List.of(10, 12, 15), 1);
        assertThat(s1.compareTo(s2)).isGreaterThan(0);
    }

}