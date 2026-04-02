package com.problems.learning.designpatterns.structural.proxy.statical.document;

import com.problems.learning.designpatterns.structural.proxy.statical.user.UserContext;

public class DocumentServiceProxy implements DocumentService {

    private final DocumentService target;
    private final UserContext userContext;

    public DocumentServiceProxy(DocumentService target, UserContext userContext) {
        this.target = target;
        this.userContext = userContext;
    }

    @Override
    public String getDocument(String docId) {

        // 1) Authorization check
        if (!userContext.hasAccess(docId)) {
            throw new RuntimeException("Access denied for user " + userContext.getUser());
        }

        // 2) Logging/Auditing
        System.out.println("AUDIT: User " + userContext.getUser()
                + " accessed doc " + docId);

        // 3) Delegate to real service
        return target.getDocument(docId);
    }
}