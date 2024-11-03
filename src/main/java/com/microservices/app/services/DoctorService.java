package com.microservices.app.services;

import com.microservices.app.display.Print;
import com.microservices.app.enums.DoctorSpecialization;
import com.microservices.app.exceptions.SlotException;
import com.microservices.app.models.AvailableDoctor;
import com.microservices.app.models.Doctor;
import com.microservices.app.models.TimeSlot;
import com.microservices.app.repositories.DoctorsRepository;
import com.microservices.app.utils.Utils;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DoctorService {

    private final DoctorsRepository doctorsRepository;
    private final Print print;

    public DoctorService(DoctorsRepository doctorsRepository, Print print) {
        this.doctorsRepository = doctorsRepository;
        this.print = print;
    }

    public void registerDoctor(Doctor doctor) {
        doctorsRepository.registerDoctor(doctor);
        print.printData(doctor.getDoctorName() + " has been registered to Flipmed'd system. Welcome! ");
    }

    public void addAvailability(Integer doctorId, TimeSlot timeSlot) {
        //Check if slot is 30 minutes

        LocalTime start = Utils.convertStringTimeToLocalTime(timeSlot.getStartTime());
        LocalTime end = Utils.convertStringTimeToLocalTime(timeSlot.getEndTime());

        if(ChronoUnit.MINUTES.between(start, end) == 30) {
            doctorsRepository.addAvailability(doctorId, timeSlot);
            print.printData("Added the time slot to your schedule, Dr. " + doctorsRepository.getDoctorDetails(doctorId).getDoctorName());
        }

        else
            throw new SlotException("Sorry, cannot add the time slot to your schedule as the slot is not of 30 minutes. Please book a 30 minutes time slot.");
    }

    public void showAvailableSlotsBySpecialization(DoctorSpecialization doctorSpecialization) throws CloneNotSupportedException {
        List<Doctor> specializedDoctors =  doctorsRepository.getDoctorsBySpecialization(doctorSpecialization);
        List<AvailableDoctor> availableSlots = doctorsRepository.getAvailableTimeSlotsForAllDoctorsForSpecialization(specializedDoctors);

        if(!availableSlots.isEmpty()) {
            print.printData("Available doctors with time slots: ");

            for(AvailableDoctor doctor : availableSlots) {
                for(TimeSlot time : doctor.getTimeSlots())
                    print.printData(doctor.getDoctor().getDoctorName() + " " + time.getStartTime() + " - " + time.getEndTime());
            }
        }

        else {
            print.printData("No Slots Available :(");
        }
    }

}
