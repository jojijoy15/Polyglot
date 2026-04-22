package com.problems.learning.designpatterns.structural.proxy.dynamic.library.service;

public class PaymentServiceImpl implements PaymentService {

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing payment of ₹ " + amount);
    }
}