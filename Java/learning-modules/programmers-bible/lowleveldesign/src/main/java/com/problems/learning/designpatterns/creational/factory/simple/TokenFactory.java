package com.problems.learning.designpatterns.creational.factory.simple;

import com.problems.learning.designpatterns.creational.factory.simple.producttype.ClientToken;
import com.problems.learning.designpatterns.creational.factory.simple.producttype.PaymentToken;
import com.problems.learning.designpatterns.creational.factory.simple.producttype.Token;
import com.problems.learning.designpatterns.creational.factory.simple.producttype.TokenType;

import java.time.LocalDateTime;

/*
    Note:
     * The Factory Design Pattern in Java is a creational pattern that defines an interface for
       creating an object but allows subclasses to alter the type of objects that will be created.
     * This pattern promotes flexibility and scalability in your codebase.
 */
public class TokenFactory {

    //Violates OCP
    public Token createToken(TokenType tokenType) {
        return switch (tokenType) {
            case CLIENT_TOKEN -> new ClientToken(LocalDateTime.now().toString(), "client", "openid, profile");
            case PAYMENT_TOKEN -> new PaymentToken(LocalDateTime.now().toString(), "client", "android");
        };
    }
}
