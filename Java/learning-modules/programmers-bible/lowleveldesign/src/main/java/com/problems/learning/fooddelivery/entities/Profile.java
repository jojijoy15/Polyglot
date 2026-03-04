package com.problems.learning.fooddelivery.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Profile {

    protected int id;
    protected String firstName;
    protected String lastName;
    protected String mobileNumber;
    protected String email;
    protected String address;
    protected float rating;
}
