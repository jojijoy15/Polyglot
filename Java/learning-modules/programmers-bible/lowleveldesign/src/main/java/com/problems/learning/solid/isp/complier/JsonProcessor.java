package com.problems.learning.solid.isp.complier;

public class JsonProcessor implements Parser {

    @Override
    public void parse(String document) {
        System.out.println("Parsing JSON document");
    }
}