package com.problems.learning.solid.lsp.complier;

/**
 * LSP Compliant Client:
 * Every subtype of StringProcessor can be substituted without breaking behavior.
 * No surprises — no exceptions, no unexpected return types.
 */
public class Client {

    public static void main(String[] args) {
        StringProcessor[] processors = {
                new JsonStringProcessor(),
                new EncodeStringProcessor(),
                new NumericStringProcessor()  // won't throw even for non-hex input
        };

        String input = """
                {"name" : "Joji"}
                """;

        for (StringProcessor processor : processors) {
            String result = processor.process(input);
            System.out.println(processor.getClass().getSimpleName() + " → " + result);
        }
    }
}

