package com.problems.learning.solid.lsp.breaker;

import java.util.Base64;

public class EncodeStringProcessor extends StringProcessor {

    @Override
    public String process(String source) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(source.getBytes());
    }
}
