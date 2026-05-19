package com.problems.learning.system.parkinglot;

public enum TicketStatus {
    ACTIVE,
    PAID,
    UNPAID   // vehicle left without paying → candidate for blacklist
}

