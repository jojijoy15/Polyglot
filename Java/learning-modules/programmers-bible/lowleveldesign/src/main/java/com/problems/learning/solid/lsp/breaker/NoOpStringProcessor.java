package com.problems.learning.solid.lsp.breaker;

// Trying to break LSP by returning Broader type
public class NoOpStringProcessor extends StringProcessor {

    @Override
    public /*CharSequence*/String process(String source) {
        return "";
    }
}
