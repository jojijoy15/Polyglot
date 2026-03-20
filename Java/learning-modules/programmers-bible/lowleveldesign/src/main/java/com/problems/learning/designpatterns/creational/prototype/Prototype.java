package com.problems.learning.designpatterns.creational.prototype;

public abstract class Prototype<T extends Prototype<T>> {

    public abstract T proto();
}
