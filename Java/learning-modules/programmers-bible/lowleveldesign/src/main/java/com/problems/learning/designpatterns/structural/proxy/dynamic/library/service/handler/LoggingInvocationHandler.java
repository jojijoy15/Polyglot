package com.problems.learning.designpatterns.structural.proxy.dynamic.library.service.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LoggingInvocationHandler implements InvocationHandler {

    private final Object target;

    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("Before method: " + method.getName());

        long start = System.currentTimeMillis();

        Object result = method.invoke(target, args);

        long end = System.currentTimeMillis();

        System.out.println("After method: " + method.getName());
        System.out.println("Execution time: " + (end - start) + " ms");

        return result;
    }
}