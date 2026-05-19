package com.problems.learning.designpatterns.creational.singleton.impl;

public final class BillPughSingleton {

    private BillPughSingleton(){}

    private static class Holder{
        private static final BillPughSingleton instance = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance(){
        return Holder.instance;
    }
}
