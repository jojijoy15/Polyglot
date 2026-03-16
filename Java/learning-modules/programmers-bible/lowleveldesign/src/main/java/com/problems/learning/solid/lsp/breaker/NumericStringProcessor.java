package com.problems.learning.solid.lsp.breaker;

import java.util.HexFormat;

//Violates LSP by strengthening the Pre condition
public class NumericStringProcessor extends StringProcessor {

    @Override
    public String process(String source) {
        if(!source.matches("^[\\dA-Fa-f]+$")){
            throw new IllegalArgumentException("Invalid numeric range");
        }
        return HexFormat.of().formatHex(source.getBytes());
    }
}
