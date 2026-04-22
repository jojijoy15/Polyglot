package com.problems.learning.designpatterns.structural.proxy.statical.client;

import com.problems.learning.designpatterns.structural.proxy.statical.document.DocumentService;
import com.problems.learning.designpatterns.structural.proxy.statical.document.DocumentServiceProxy;
import com.problems.learning.designpatterns.structural.proxy.statical.document.RealDocumentService;
import com.problems.learning.designpatterns.structural.proxy.statical.user.UserContext;

public class Client {

    public static void main(String[] args) {

        UserContext user = new UserContext("alice");
        DocumentService proxy = new DocumentServiceProxy(new RealDocumentService(), user);

        System.out.println(proxy.getDocument("public-123")); // allowed

        System.out.println(proxy.getDocument("secret-456")); // throws exception
    }
}