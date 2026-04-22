package com.problems.learning.solid.lsp.complier;

import java.util.Base64;

// LSP Compliant: accepts any string (same precondition as parent), returns a string
public class EncodeStringProcessor extends StringProcessor {

    @Override
    public String process(String source) {
        return Base64.getEncoder().encodeToString(source.getBytes());
    }
}

