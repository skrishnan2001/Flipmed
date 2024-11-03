package com.microservices.app.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Booking {

    private Integer bookingId;
    private Doctor doctor;
    private Patient patient;
    private TimeSlot timeSlot;
    private Boolean isWaitListed;

    public Booking(Integer bookingId, Doctor doctor, Patient patient, TimeSlot timeSlot) {
        this.bookingId = bookingId;
        this.doctor = doctor;
        this.patient = patient;
        this.timeSlot = timeSlot;
        this.isWaitListed = false;
    }

}
