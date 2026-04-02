package com.problems.learning.designpatterns.creational.factory.simple.producttype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public abstract class Token {

    private String expiry;
    private String subject;
}
