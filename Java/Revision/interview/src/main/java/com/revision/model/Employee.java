package com.revision.model;

public record Employee(String firstName, String lastName, int age,
                       double salary, /*Not the best way to represent salary*/
                       int band) {

}
