package com.problems.learning.solid.isp.complier;

public class PdfProcessor implements Parser, PdfConverter {

    @Override
    public void parse(String document) {
        System.out.println("Parsing document");
    }

    @Override
    public void convertToPdf(String document) {
        System.out.println("Converting to PDF");
    }
}