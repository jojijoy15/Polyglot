package com.problems.learning.solid.lsp.complier;

import java.util.HexFormat;

/**
 * LSP Compliant: Does NOT strengthen the precondition.
 * Accepts any string (like the parent). If the input is not numeric/hex,
 * it gracefully returns the original string instead of throwing an exception.
 */
public class NumericStringProcessor extends StringProcessor {

    @Override
    public String process(String source) {
        if (!source.matches("^[\\dA-Fa-f]+$")) {
            return source; // graceful fallback instead of throwing
        }
        return HexFormat.of().formatHex(source.getBytes());
    }
}

