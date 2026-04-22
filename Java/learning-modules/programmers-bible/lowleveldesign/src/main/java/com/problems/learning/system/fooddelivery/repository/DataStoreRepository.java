package com.problems.learning.system.fooddelivery.repository;

import com.problems.learning.system.fooddelivery.entities.*;
import com.problems.learning.system.fooddelivery.enums.DeliveryStatus;
import com.problems.learning.system.fooddelivery.enums.Urgency;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataStoreRepository {
    public static final List<Restaurant> restaurants;
    public static final List<Order> orders;
    public static final List<Rider> riders;
    public static final List<Customer> customers;

    static {
        restaurants= fetchAllRestaurants();
        customers = fetchAllCustomers();
        riders = fetchAllRiders();
        orders= fetchAllOrders();
    }

    public static List<Rider> fetchAllRiders() {
        Rider arjun = Rider.builder()
                .id(1)
                .firstName("Arjun")
                .lastName("R01")
                .isAvailable(true)
                .mobileNumber("1234567890")
                .email("arjunr01@mail.com")
                .address("Arjun's Address")
                .orders(new ArrayList<>())
                .rating(0.0f)
                .build();
        Rider sara = Rider.builder()
                .id(2)
                .firstName("Sara")
                .lastName("R02")
                .isAvailable(true)
                .mobileNumber("1234567890")
                .email("sarar02@mail.com")
                .address("Sara's Address")
                .orders(new ArrayList<>())
                .rating(0.0f)
                .build();
        Rider ravi = Rider.builder()
                .id(3)
                .firstName("Ravi")
                .lastName("R03")
                .isAvailable(true)
                .mobileNumber("1234567890")
                .email("ravir03@mail.com")
                .address("Ravi's Address")
                .orders(new ArrayList<>())
                .rating(0.0f)
                .build();
        return List.of(arjun, sara, ravi);
    }

    public static List<Customer> fetchAllCustomers() {

        Customer priya = Customer.builder()
                .id(1)
                .firstName("Priya")
                .lastName("C01")
                .mobileNumber("1234567890")
                .email("priyac01@mail.com")
                .address("Priya's Address")
                .orders(new ArrayList<>())
                .rating(0.0f)
                .build();
        Customer rahul = Customer.builder()
                .id(2)
                .firstName("Rahul")
                .lastName("C02")
                .mobileNumber("1234567890")
                .email("rahulc02@mail.com")
                .address("Rahul's Address")
                .orders(new ArrayList<>())
                .rating(0.0f)
                .build();

        Customer meena = Customer.builder()
                .id(3)
                .firstName("Meena")
                .lastName("C03")
                .mobileNumber("1234567890")
                .email("minac03@mail.com")
                .address("Mina's Address")
                .orders(new ArrayList<>())
                .rating(0.0f)
                .build();

        Customer dev = Customer.builder()
                .id(4)
                .firstName("Dev")
                .lastName("C04")
                .mobileNumber("1234567890")
                .email("devc04@mail.com")
                .address("Dev's Address")
                .orders(new ArrayList<>())
                .rating(0.0f)
                .build();
        return List.of(priya, rahul, meena, dev);
    }


    public static List<Order> fetchAllOrders() {

        Order rahulOrder = Order.builder()
                .orderId("ORD-02")
                .items(List.of(new Item(1, "Paneer Tikka")))
                .urgency(Urgency.URGENT)
                .customer(customers.get(1))
                .restaurant(restaurants.get(0))
                .deliveryStatus(DeliveryStatus.PENDING)
                .creationTime(LocalDateTime.now())
                .build();
        customers.get(1).addOrder(rahulOrder);

        Order priyaOrder = Order.builder()
                .orderId("ORD-01")
                .items(List.of(new Item(1, "Kachori")))
                .urgency(Urgency.NORMAL)
                .customer(customers.get(0))
                .restaurant(restaurants.get(3))
                .deliveryStatus(DeliveryStatus.PENDING)
                .creationTime(LocalDateTime.now())
                .build();
        customers.get(0).addOrder(priyaOrder);


        Order meenaOrder = Order.builder()
                .orderId("ORD-03")
                .items(List.of(new Item(1, "Rajma Chawal")))
                .urgency(Urgency.SCHEDULED)
                .customer(customers.get(2))
                .restaurant(restaurants.get(2))
                .deliveryStatus(DeliveryStatus.PENDING)
                .creationTime(LocalDateTime.now())
                .build();
        customers.get(2).addOrder(meenaOrder);

        Order devOrder = Order.builder()
                .orderId("ORD-04")
                .items(List.of(new Item(1, "Jalebi Fafda")))
                .urgency(Urgency.URGENT)
                .customer(customers.get(3))
                .restaurant(restaurants.get(1))
                .deliveryStatus(DeliveryStatus.PENDING)
                .creationTime(LocalDateTime.now())
                .build();
        customers.get(3).addOrder(devOrder);

        return List.of(rahulOrder, priyaOrder, meenaOrder, devOrder);
    }

    public static List<Restaurant>  fetchAllRestaurants() {
        Restaurant indiraNagarRestaurant = Restaurant.builder()
                .restaurantId(1)
                .restaurantName("Indiranagar Bites")
                .build();
        Restaurant whiteFieldEats = Restaurant.builder()
                .restaurantId(2)
                .restaurantName("WhiteField Eats")
                .build();
        Restaurant hsrDiner = Restaurant.builder()
                .restaurantId(3)
                .restaurantName("HSR Diner")
                .build();
        Restaurant kormangalaKitchen = Restaurant.builder()
                .restaurantId(5)
                .restaurantName("Kormangala Kitchen")
                .build();

        return List.of(indiraNagarRestaurant, whiteFieldEats, hsrDiner, kormangalaKitchen);
    }


}
