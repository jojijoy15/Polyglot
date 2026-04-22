package com.problems.learning.solid.lsp.complier;

/**
 * Base contract: accepts ANY string, returns a processed string.
 * Subtypes must NOT strengthen this precondition or weaken the postcondition.
 */
public abstract class StringProcessor {

    public abstract String process(String source);

}

