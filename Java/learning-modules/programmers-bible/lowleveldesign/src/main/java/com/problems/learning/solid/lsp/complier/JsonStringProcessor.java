package com.problems.learning.solid.lsp.complier;

// LSP Compliant: accepts any string, formats it if it looks like JSON, returns as-is otherwise
public class JsonStringProcessor extends StringProcessor {

    @Override
    public String process(String source) {
        source = source.replaceAll("\\{(.*)}", "\r\n{\r\n\t$1\r\n}\r\n");
        return source;
    }
}

