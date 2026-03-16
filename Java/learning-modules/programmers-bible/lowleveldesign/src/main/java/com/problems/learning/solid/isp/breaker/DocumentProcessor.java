package com.problems.learning.solid.isp.breaker;

public interface DocumentProcessor {

    void parse(String document);

    void convertToPdf(String document);

    void translate(String document);
}