package com.problems.learning.system.fooddelivery.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Restaurant {

    private int restaurantId;
    private String restaurant_name;
    private String restaurant_address;
    private String restaurantName;
    private String address;
    private float rating;
}
