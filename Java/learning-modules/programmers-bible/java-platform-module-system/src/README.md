# Java Platform Module System (JPMS) Demo

#### Classpath (unnamed module) — works, but Demo 4 shows limited info
mvn exec:java

#### Module path (named module) — full JPMS behavior
java --module-path target/classes -m jpms.learning/com.problems.learning.jpms.JPMSDemo