package com.problems.learning.designpatterns.creational.factory.simple.producttype;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentToken extends Token {

    private String device;

    public PaymentToken(String expiry, String subject, String device) {
        super(expiry, subject);
        this.device = device;
    }


}
