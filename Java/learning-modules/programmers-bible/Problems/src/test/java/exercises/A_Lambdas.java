//package exercises;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.function.BiFunction;
//import java.util.function.Consumer;
//import java.util.function.Function;
//import java.util.function.Predicate;
//import java.util.function.Supplier;
//
//import org.junit.Test;
//
///**
// * This set of exercises is about lambdas and method references.
// * You will write a lambda or method reference corresponding to
// * each of several different functional interfaces. Each exercise
// * is named after the functional interface intended to be used
// * as the solution.
// */
//public class A_Lambdas {
//
//    /**
//     * Write a lambda expression that is a predicate
//     * that tests whether a string is longer than four characters.
//     */
//    @Test
//    public void a_predicate1() {
//        Predicate<String> pred = e -> e.length() > 4;
//
//        assertTrue(pred.test("abcde"));
//        assertFalse(pred.test("abcd"));
//    }
//
//    /**
//     * Write a lambda expression that is a predicate
//     * that tests whether a string is empty.
//     */
//    @Test
//    public void a_predicate2() {
//        Predicate<String> pred = e -> e.isEmpty();
//
//        assertTrue(pred.test(""));
//        assertFalse(pred.test("a"));
//    }
//
//    /**
//     * Write an unbound method reference that is a predicate
//     * that tests whether a string is empty. An unbound method
//     * reference has a class name on the left-hand side of the ::
//     * operator:
//     *
//     *     classname::methodname
//     */
//    @Test
//    public void a_predicate3() {
//        Predicate<String> pred = String::isEmpty;
//
//        assertTrue(pred.test(""));
//        assertFalse(pred.test("a"));
//    }
//    // Hint:
//    // <editor-fold defaultstate="collapsed">
//    // Copy the lambda expression from the previous exercise and then pop
//    // up the menu over the "light bulb" icon in the left margin. This menu
//    // has an option to convert the lambda to a method reference. (The exact
//    // gesture will vary among IDEs.)
//    // </editor-fold>
//
//    /**
//     * Create a predicate that returns true if both predicates
//     * startsWithJ and lengthIs7 hold.
//     */
//    @Test
//    public void a_predicate4() {
//        Predicate<String> startsWithJ = s -> s.startsWith("J");
//        Predicate<String> lengthIs7 = s -> s.length() == 7;
//
//        Predicate<String> startsWithJAndLengthIs7 = startsWithJ.and(lengthIs7);
//        assertFalse(startsWithJAndLengthIs7.test("Hello"));
//        assertFalse(startsWithJAndLengthIs7.test("HelloJ1"));
//        assertFalse(startsWithJAndLengthIs7.test("Java1"));
//        assertTrue(startsWithJAndLengthIs7.test("JavaOne"));
//    }
//
//
//    /**
//     * Create a predicate that is true if the length of the provided string
//     * is 9 or the provided string equals ERROR.
//     */
//    @Test
//    public void a_predicate5() {
//        Predicate<String> lengthIs9 = s -> s.length() == 9;
//        Predicate<String> equalsError = "ERROR"::equals;
//        // Note: this could also be: Predicate.isEqual("ERROR")
//
//        Predicate<String> lengthIs9orError = lengthIs9.or(equalsError);
//
//        assertFalse(lengthIs9orError.test("Hello"));
//        assertTrue(lengthIs9orError.test("Hello J1!"));
//        assertTrue(lengthIs9orError.test("ERROR"));
//        assertFalse(lengthIs9orError.test("Error"));
//    }
//
//    /**
//     * Write a lambda expression that wraps the given
//     * string in parentheses.
//     */
//    @Test
//    public void b_function1() {
//        Function<String, String> func = e -> "("+ e + ")";
//
//        assertEquals("(abc)", func.apply("abc"));
//    }
//
//    /**
//     * Write a lambda expression that converts the
//     * given string to upper case.
//     */
//    @Test
//    public void b_function2() {
//        Function<String, String> func = e -> e.toUpperCase();
//
//        assertEquals("ABC", func.apply("abc"));
//    }
//
//    /**
//     * Write an unbound method reference that converts the
//     * given string to upper case.
//     */
//    @Test
//    public void b_function3() {
//        Function<String, String> func = String::toUpperCase;
//
//        assertEquals("ABC", func.apply("abc"));
//    }
//
//    /**
//     * Given two Functions, one that converts a null reference to an
//     * empty string, and another that gets the length of a string,
//     * create a single function converts nulls and then gets the
//     * string's length.
//     */
//    @Test
//    public void b_function4() {
//        Function<String, String> unNullify = s -> s == null ? "" : s;
//        Function<String, Integer> length = String::length;
//
//        Function<String, Integer> lengthBis = length.compose(unNullify);
//
//        assertEquals((Integer)14, lengthBis.apply("Hello JavaOne!"));
//        assertEquals((Integer)0, lengthBis.apply(""));
//        assertEquals((Integer)0, lengthBis.apply(null));
//    }
//    // Hint:
//    // <editor-fold defaultstate="collapsed">
//    // See java.util.Function.andThen() or java.util.Function.compose()
//    // </editor-fold>
//
//    /**
//     * Write a lambda expression that appends the
//     * string "abc" to the given StringBuilder.
//     */
//    @Test
//    public void c_consumer1() {
//        Consumer<StringBuilder> cons = e -> e.append("abc");
//        StringBuilder sb = new StringBuilder("xyz");
//        cons.accept(sb);
//        assertEquals("xyzabc", sb.toString());
//    }
//
//    /**
//     * Write a lambda expression that clears the given list.
//     */
//    @Test
//    public void c_consumer2() {
//        Consumer<List<String>> cons = e -> e.clear();
//
//        //List<String> list = new ArrayList<>(List.of("a", "b", "c")); //java 9 and above
//        List<String> list  = new ArrayList<>();
//        Collections.addAll(list, "a", "b", "c");
//        cons.accept(list);
//        assertTrue(list.isEmpty());
//    }
//
//    /**
//     * Write an unbound method reference that clears the given list.
//     */
//    @Test
//    public void c_consumer3() {
//        Consumer<List<String>> cons = List::clear; // TODO
//
//        //List<String> list = new ArrayList<>(List.of("a", "b", "c")); //java 9 and above
//        List<String> list  = new ArrayList<>();
//        Collections.addAll(list, "a", "b", "c");
//        cons.accept(list);
//        assertTrue(list.isEmpty());
//    }
//
//    /**
//     * Given two consumers, create a consumer that passes the String to the
//     * first consumer, then to the second.
//     */
//    @Test
//    public void c_consumer4() {
//        Consumer<List<String>> c1 = list -> list.add("first");
//        Consumer<List<String>> c2 = list -> list.add("second");
//
//        Consumer<List<String>> consumer = c1.andThen(c2);
//        List<String> list  = new ArrayList<>();
//        Collections.addAll(list, "a", "b", "c");
//        consumer.accept(list);
//
//        List<String> testlist  = new ArrayList<>();
//        Collections.addAll(testlist, "a", "b", "c", "first", "second");
//
//        assertEquals(testlist, list);
//    }
//
//    /**
//     * Write a lambda expression that returns a new StringBuilder
//     * containing the string "abc".
//     */
//    @Test
//    public void d_supplier1() {
//        Supplier<StringBuilder> sup = () -> new StringBuilder("abc");
//        assertEquals("abc", sup.get().toString());
//    }
//
//    /**
//     * Write a lambda expression that returns a new, empty StringBuilder.
//     */
//    @Test
//    public void d_supplier2() {
//        Supplier<StringBuilder> sup = () -> new StringBuilder();
//        assertEquals("", sup.get().toString());
//    }
//
//    /**
//     * Write an unbound method reference that returns a new, empty StringBuilder.
//     */
//    @Test
//    public void d_supplier3() {
//        Supplier<StringBuilder> sup = StringBuilder::new;
//
//        assertEquals("", sup.get().toString());
//    }
//
//    /**
//     * Write a lambda expression that, given two strings, returns the result
//     * of concatenating the first with the second, followed by the
//     * first again.
//     */
//    @Test
//    public void e_bifunction1() {
//        BiFunction<String, String, String> bifunc = (a,b) -> a + b + a;
//
//        assertEquals("FirstSecondFirst", bifunc.apply("First", "Second"));
//    }
//
//    /**
//     * Write a lambda expression that returns the index of
//     * the first occurrence of the second string within the first string,
//     * or -1 if the second string doesn't occur within the first string.
//     */
//    @Test
//    public void e_bifunction2() {
//        BiFunction<String, String, Integer> bifunc = (a, b) -> a.indexOf(b);
//        assertEquals(3, bifunc.apply("abcdefghi", "def").intValue());
//        assertEquals(-1, bifunc.apply("abcdefghi", "xyz").intValue());
//    }
//
//    /**
//     * Write an unbound method reference that returns the index of
//     * the first occurrence of the second string within the first string,
//     * or -1 if the second string doesn't occur within the first string.
//     */
//    @Test
//    public void e_bifunction3() {
//        BiFunction<String, String, Integer> bifunc = String::indexOf;
//
//        assertEquals(3, bifunc.apply("abcdefghij", "def").intValue());
//        assertEquals(-1, bifunc.apply("abcdefghij", "xyz").intValue());
//    }
//
//    /**
//     * Write a lambda expression that appends the 'suffix'
//     * variable (a String) to the sb variable (a StringBuilder).
//     */
//    @Test
//    public void f_runnable1() {
//        StringBuilder sb = new StringBuilder("abc");
//        String suffix = "xyz";
//
//        Runnable r = () -> sb.append(suffix);
//
//        r.run();
//        r.run();
//        r.run();
//        assertEquals("abcxyzxyzxyz", sb.toString());
//    }
//
//    /**
//     * Write a lambda expression that takes a string argument
//     * and returns the index of that argument into the string
//     * "abcdefghij", or that returns -1 if the string argument
//     * doesn't occur.
//     */
//    @Test
//    public void g_boundMethodRef1() {
//        Function<String, Integer> func = e -> "abcdefghij".indexOf(e);
//
//        assertEquals(2, func.apply("cde").intValue());
//        assertEquals(4, func.apply("efg").intValue());
//        assertEquals(-1, func.apply("xyz").intValue());
//    }
//    // Hint:
//    // <editor-fold defaultstate="collapsed">
//    // Call the indexOf() method on a string literal.
//    // </editor-fold>
//
//    /**
//     * Write a bound method reference that takes a string argument
//     * and returns the index of that argument into the string
//     * "abcdefghij", or that returns -1 if the string argument
//     * doesn't occur. A bound method reference has an instance,
//     * or an expression that evaluates to an instance, on the left-hand
//     * side of the :: operator:
//     *
//     *     myObject::methodname
//     *
//     * This is in contrast to an unbound method reference, which has
//     * a classname on the left-hand side of the :: operator.
//     */
//    @Test
//    public void g_boundMethodRef2() {
//        Function<String, Integer> func = "abcdefghij"::indexOf; // TODO
//
//        assertEquals(2, func.apply("cde").intValue());
//        assertEquals(4, func.apply("efg").intValue());
//        assertEquals(-1, func.apply("xyz").intValue());
//    }
//    // Hint:
//    // <editor-fold defaultstate="collapsed">
//    // Place a string literal on the left-hand side of the :: operator.
//    // </editor-fold>
//}
