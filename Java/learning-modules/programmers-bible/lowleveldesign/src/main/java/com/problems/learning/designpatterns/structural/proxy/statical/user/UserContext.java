package com.problems.learning.designpatterns.structural.proxy.statical.user;

public class UserContext {
    private final String user;

    public UserContext(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public boolean hasAccess(String docId) {
        // Simple rule: only "admin" can access all docs
        return "admin".equals(user) || docId.startsWith("public");
    }
}