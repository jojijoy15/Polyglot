package com.problems.learning.solid.ocp.breaker;


import com.problems.learning.solid.ocp.breaker.vehicles.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Slot {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Vehicle vehicle;
}
