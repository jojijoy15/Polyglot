package com.problems.learning.solid.lsp.breaker;

public class Client {

    public static void main(String[] args) {
        StringProcessor stringProcessor = new JsonStringProcessor();
        String processed = stringProcessor.process("""
            {"name" : "Joji"}
        """);
        System.out.println(processed);
        stringProcessor = new EncodeStringProcessor();
        processed = stringProcessor.process("""
           {"name" : "Joji"}
        """);
        System.out.println(processed);
    }
}
