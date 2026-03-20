package com.problems.learning.designpatterns.creational.factory.producttype;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Setter
@Getter
public class ClientToken extends Token {

    private List<String> claims;

    public ClientToken(String expiry, String subject, String claims) {
        super(expiry, subject);
        this.claims = Arrays.asList(claims.split(","));
    }
}
