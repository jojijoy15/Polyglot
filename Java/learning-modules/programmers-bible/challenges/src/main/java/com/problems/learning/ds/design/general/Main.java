package com.problems.learning.ds.design.general;

public class Main {
    public static void main(String[] args) throws Exception {

        String xml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <company name="TechCorp" country="India">
                <department id="1" type="Engineering">
                    <name>Backend</name>
                    <headcount>50</headcount>
                    <budget>500000.00</budget>
                    <active>true</active>
                    <employees>
                        <employee id="E001">
                            <name>Alice</name>
                            <role>Senior Engineer</role>
                            <salary>120000</salary>
                        </employee>
                        <employee id="E002">
                            <name>Bob</name>
                            <role>Junior Engineer</role>
                            <salary>80000</salary>
                        </employee>
                    </employees>
                </department>
                <department id="2" type="HR">
                    <name>Human Resources</name>
                    <headcount>10</headcount>
                    <budget>100000.00</budget>
                    <active>false</active>
                </department>
            </company>
            """;

        // ── 1. Parse XML ──────────────────────────────────────────
        XmlNode root = XmlNode.fromString(xml);
        System.out.println("=== Pretty Print ===");
        root.prettyPrint();

        // ── 2. Read attributes ────────────────────────────────────
        System.out.println("\n=== Company Info ===");
        System.out.println("Name    : " + root.getAttribute("name", "Unknown"));
        System.out.println("Country : " + root.getAttribute("country", "Unknown"));

        // ── 3. Get all departments ────────────────────────────────
        System.out.println("\n=== Departments ===");
        root.getChildren("department").forEach(dept -> {
            System.out.println("Dept ID   : " + dept.getAttribute("id", "-"));
            System.out.println("Type      : " + dept.getAttribute("type", "-"));
            System.out.println("Name      : " + dept.getChildText("name").orElse("N/A"));
            System.out.println("Headcount : " + dept.getChildInt("headcount").orElse(0));
            System.out.println("Budget    : " + dept.getChildDouble("budget").orElse(0.0));
            System.out.println("Active    : " + dept.getChildBool("active").orElse(false));
            System.out.println();
        });

        // ── 4. Deep search across the whole tree ──────────────────
        System.out.println("=== All Employees (Deep Search) ===");
        root.findAll("employee").forEach(emp -> {
            System.out.println("ID     : " + emp.getAttribute("id", "-"));
            System.out.println("Name   : " + emp.getChildText("name").orElse("N/A"));
            System.out.println("Role   : " + emp.getChildText("role").orElse("N/A"));
            System.out.println("Salary : " + emp.getChildInt("salary").orElse(0));
            System.out.println();
        });

        // ── 5. Total salary using Streams ─────────────────────────
        int totalSalary = root.findAll("employee").stream()
                .mapToInt(e -> e.getChildInt("salary").orElse(0))
                .sum();
        System.out.println("Total Salary : " + totalSalary);

        // ── 6. Flat map representation ────────────────────────────
        System.out.println("\n=== Flat Map ===");
        root.toFlatMap(".").forEach((k, v) -> System.out.println(k + " = " + v));

        // ── 7. Convert back to XML string ─────────────────────────
        System.out.println("\n=== Back to XML ===");
        System.out.println(root.toXmlString());
    }
}