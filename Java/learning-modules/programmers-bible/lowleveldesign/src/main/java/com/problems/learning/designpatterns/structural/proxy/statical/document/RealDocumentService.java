package com.problems.learning.designpatterns.structural.proxy.statical.document;

public class RealDocumentService implements DocumentService {

    @Override
    public String getDocument(String docId) {
        // Simulate DB or storage fetch
        return "Content of document " + docId;
    }
}