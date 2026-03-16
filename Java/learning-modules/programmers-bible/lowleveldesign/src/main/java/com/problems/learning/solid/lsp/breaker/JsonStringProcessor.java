package com.problems.learning.solid.lsp.breaker;

public class JsonStringProcessor extends StringProcessor {

    @Override
    public String process(String source) {
        source = source.replaceAll("\\{(.*)}", "\r\n{\r\n\t$1\r\n}\r\n");
        return source;
    }

}
