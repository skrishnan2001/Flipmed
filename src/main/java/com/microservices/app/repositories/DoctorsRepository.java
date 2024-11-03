package com.microservices.app.repositories;

import com.microservices.app.enums.DoctorSpecialization;
import com.microservices.app.exceptions.DoctorAlreadyPresentException;
import com.microservices.app.exceptions.DoctorNotPresentException;
import com.microservices.app.exceptions.NoSpecializationPresentException;
import com.microservices.app.exceptions.SlotException;
import com.microservices.app.models.AvailableDoctor;
import com.microservices.app.models.Doctor;
import com.microservices.app.models.TimeSlot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorsRepository {

    private final Map<Integer, Doctor> doctors;
    private final Map<DoctorSpecialization, List<Doctor>> doctorsBySpecialization;

    public DoctorsRepository() {
        this.doctors = new HashMap<>();
        this.doctorsBySpecialization = new HashMap<>();
    }

    public void registerDoctor(Doctor doctor) {
        if(doctors.containsKey(doctor.getDoctorId()))
            throw new DoctorAlreadyPresentException("This doctor is already registered!!");

        doctors.put(doctor.getDoctorId(), doctor);

        if(!doctorsBySpecialization.containsKey(doctor.getDoctorSpecialization()))
            doctorsBySpecialization.put(doctor.getDoctorSpecialization(), new ArrayList<>());

        doctorsBySpecialization.get(doctor.getDoctorSpecialization()).add(doctor);
    }

    public void addAvailability(Integer doctorId, TimeSlot timeSlot) {
        if(!doctors.containsKey(doctorId))
            throw new DoctorNotPresentException("No doctor exists in the system with the given doctor id!");

        Doctor doctor = doctors.get(doctorId);

        doctor.getAvailableSlots().put(timeSlot, true);
    }

    public List<Doctor> getDoctorsBySpecialization(DoctorSpecialization doctorSpecialization) {
        if(!doctorsBySpecialization.containsKey(doctorSpecialization))
            throw new NoSpecializationPresentException("No doctors found in the system for the given specialization!");

        return doctorsBySpecialization.get(doctorSpecialization);
    }

    public List<AvailableDoctor> getAvailableTimeSlotsForAllDoctorsForSpecialization(List<Doctor> specializedDoctors) throws CloneNotSupportedException {
        List<AvailableDoctor> doctorsWithAvailableSlots = new ArrayList<>();

        for(Doctor doctor : specializedDoctors) {
            AvailableDoctor availableDoctor = new AvailableDoctor();
            availableDoctor.setDoctor(doctor.clone());
            List<TimeSlot> availableSlots = new ArrayList<>();

            for(TimeSlot slot : doctor.getAvailableSlots().keySet()) {
                if(doctor.getAvailableSlots().get(slot))
                    availableSlots.add(slot);
            }

            availableDoctor.setTimeSlots(availableSlots);
            doctorsWithAvailableSlots.add(availableDoctor);
        }

        return doctorsWithAvailableSlots;
    }

    public boolean isDoctorRegistered(Integer doctorId){
        return doctors.containsKey(doctorId);
    }

    public Doctor getDoctorDetails(Integer doctorId){
        return doctors.get(doctorId);
    }

    public void freeSlot(Integer doctorId, TimeSlot timeSlot) {
        if(!doctors.containsKey(doctorId))
            throw new DoctorNotPresentException();

        Map<TimeSlot, Boolean> availableSlots = doctors.get(doctorId).getAvailableSlots();

        if(!availableSlots.containsKey(timeSlot))
                throw new SlotException("Slot not found!!");

        if(!availableSlots.get(timeSlot))
            availableSlots.put(timeSlot, true);

    }

}
