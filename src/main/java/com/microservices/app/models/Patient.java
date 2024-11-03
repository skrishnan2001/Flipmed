package com.microservices.app.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Patient {

    private Integer patientId;
    private String patientName;
    private Map<Doctor, List<TimeSlot>> bookedSlots;

    private static Integer uniqueId = 1;

    public Patient(String patientName) {
        this.patientId = uniqueId++;
        this.patientName = patientName;
    }

}
