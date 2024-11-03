package com.microservices.app.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AvailableDoctor {

    private Doctor doctor;
    private List<TimeSlot> timeSlots;

}
