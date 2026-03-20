package com.problems.learning.designpatterns.creational.factory;

import com.problems.learning.designpatterns.creational.factory.producttype.ClientToken;
import com.problems.learning.designpatterns.creational.factory.producttype.PaymentToken;
import com.problems.learning.designpatterns.creational.factory.producttype.Token;
import com.problems.learning.designpatterns.creational.factory.producttype.TokenType;

import java.time.LocalDateTime;

public class TokenFactory {

    public Token createToken(TokenType tokenType) {
        return switch(tokenType) {
            case CLIENT_TOKEN -> new ClientToken(LocalDateTime.now().toString(), "client", "openid, profile");
            case PAYMENT_TOKEN -> new PaymentToken(LocalDateTime.now().toString(), "client", "android");
        };
    }
}
