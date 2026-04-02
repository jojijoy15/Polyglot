package com.problems.learning.designpatterns.structural.proxy.dynamic.client;

import com.problems.learning.designpatterns.structural.proxy.dynamic.library.service.PaymentService;
import com.problems.learning.designpatterns.structural.proxy.dynamic.library.service.factory.PaymentServiceFactory;

public class Platform {

    public static void main(String[] args) {

        PaymentService paymentService = PaymentServiceFactory.create();
        paymentService.processPayment(500);
    }
}