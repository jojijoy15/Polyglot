package com.problems.learning.solid.isp.breaker;

public class JsonProcessor implements DocumentProcessor {

    @Override
    public void parse(String document) {
        System.out.println("Parsing JSON");
    }

    @Override
    public void convertToPdf(String document) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void translate(String document) {
        //Fails ISP because, this processor is
        // implementing too many things which is not needed
        throw new UnsupportedOperationException();
    }
}