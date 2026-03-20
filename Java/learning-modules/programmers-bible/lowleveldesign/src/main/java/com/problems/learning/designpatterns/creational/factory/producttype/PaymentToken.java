package com.problems.learning.designpatterns.creational.factory.producttype;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class PaymentToken extends Token {

    private String device;

    public PaymentToken(String expiry, String subject, String device) {
        super(expiry, subject);
        this.device = device;
    }


}
