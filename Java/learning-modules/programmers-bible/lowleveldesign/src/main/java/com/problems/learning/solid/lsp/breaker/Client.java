package com.problems.learning.solid.lsp.breaker;

/**
 * LSP Violation Demo:
 * Substituting NumericStringProcessor for StringProcessor BREAKS the client
 * because it throws IllegalArgumentException for non-hex input —
 * strengthening the precondition that the parent never imposed.
 */
public class Client {

    public static void main(String[] args) {
        StringProcessor[] processors = {
                new JsonStringProcessor(),
                new EncodeStringProcessor(),
                new NumericStringProcessor()  // LSP VIOLATION: throws for non-hex input
        };

        String input = """
                {"name" : "Joji"}
                """;

        for (StringProcessor processor : processors) {
            // This loop crashes at NumericStringProcessor — you can't safely
            // substitute it wherever a StringProcessor is expected.
            String result = processor.process(input);
            System.out.println(processor.getClass().getSimpleName() + " → " + result);
        }
    }
}
