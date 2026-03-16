package com.problems.learning.solid.ocp.complier;


import com.problems.learning.solid.ocp.complier.vehicles.Vehicle;
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
