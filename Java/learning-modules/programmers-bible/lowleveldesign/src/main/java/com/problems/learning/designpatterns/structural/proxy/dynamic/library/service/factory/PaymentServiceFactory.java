package com.problems.learning.designpatterns.structural.proxy.dynamic.library.service.factory;

import com.problems.learning.designpatterns.structural.proxy.dynamic.library.service.PaymentService;
import com.problems.learning.designpatterns.structural.proxy.dynamic.library.service.PaymentServiceImpl;
import com.problems.learning.designpatterns.structural.proxy.dynamic.library.service.handler.LoggingInvocationHandler;

import java.lang.reflect.Proxy;

public class PaymentServiceFactory {

    public static PaymentService create() {
        PaymentServiceImpl realService = new PaymentServiceImpl();

        return (PaymentService) Proxy.newProxyInstance(
                PaymentService.class.getClassLoader(),
                new Class[]{PaymentService.class},
                new LoggingInvocationHandler(realService)
        );
    }
}