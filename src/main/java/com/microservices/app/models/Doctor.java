package com.microservices.app.models;

import com.microservices.app.enums.DoctorSpecialization;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Doctor implements Cloneable {

    private Integer doctorId;
    private String doctorName;
    private Map<TimeSlot, Boolean> availableSlots;
    private DoctorSpecialization doctorSpecialization;
    private Integer rating;

    private static Integer uniqueId = 1;

    public Doctor(String doctorName, Map<TimeSlot, Boolean> availableSlots, DoctorSpecialization doctorSpecialization) {
        this.doctorId = uniqueId++;
        this.doctorName = doctorName;
        this.availableSlots = availableSlots;
        this.doctorSpecialization = doctorSpecialization;
    }

    public Doctor clone() throws CloneNotSupportedException {
        return (Doctor) super.clone();
    }
}
