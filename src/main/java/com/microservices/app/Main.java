package com.microservices.app;

import com.microservices.app.display.ConsolePrint;
import com.microservices.app.display.Print;
import com.microservices.app.enums.DoctorSpecialization;
import com.microservices.app.exceptions.BookingNotPresentException;
import com.microservices.app.exceptions.SlotException;
import com.microservices.app.models.Doctor;
import com.microservices.app.models.Patient;
import com.microservices.app.models.TimeSlot;
import com.microservices.app.repositories.DoctorsRepository;
import com.microservices.app.repositories.PatientsRepository;
import com.microservices.app.services.BookingService;
import com.microservices.app.services.DoctorService;
import com.microservices.app.services.PatientService;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        Print print = new ConsolePrint();
        DoctorsRepository doctorsRepository = new DoctorsRepository();
        PatientsRepository patientsRepository = new PatientsRepository();

        DoctorService doctorService = new DoctorService(doctorsRepository, print);
        PatientService patientService = new PatientService(patientsRepository, print);
        BookingService bookingService = new BookingService(doctorsRepository, patientsRepository, print);

        //Doctors
        Doctor doctorCurious = new Doctor("Curious", new HashMap<>(), DoctorSpecialization.CARDIOLOGIST);
        Doctor doctorDreadful = new Doctor("Dreadful",new HashMap<>(), DoctorSpecialization.DERMATOLOGIST);

        //Patients
        Patient patientA = new Patient("Patient_A");
        Patient patientB = new Patient("Patient_B");
        Patient patientC = new Patient("Patient_C");

        // Registering doctor
        doctorService.registerDoctor(doctorCurious);
        doctorService.registerDoctor(doctorDreadful);

        // Add invalid Availability

        try {
            doctorService.addAvailability(doctorCurious.getDoctorId(), new TimeSlot("9:30", "10:30"));
        }
        catch (SlotException e) {
            print.printData(e.getMessage());
        }

        // Add Valid Availabilities 9:30-10:00, 12:30-13:00, 16:00-16:30
        doctorService.addAvailability(doctorCurious.getDoctorId(), new TimeSlot("9:30","10:00"));
        doctorService.addAvailability(doctorCurious.getDoctorId(), new TimeSlot("12:30","13:00"));
        doctorService.addAvailability(doctorCurious.getDoctorId(), new TimeSlot("16:00","16:30"));
        doctorService.addAvailability(doctorDreadful.getDoctorId(), new TimeSlot("12:30","13:00"));
        doctorService.addAvailability(doctorDreadful.getDoctorId(), new TimeSlot("13:07","13:37"));

        doctorService.showAvailableSlotsBySpecialization(DoctorSpecialization.CARDIOLOGIST);

        patientService.registerPatient(patientA);
        patientService.registerPatient(patientB);
        patientService.registerPatient(patientC);

        bookingService.bookAppointment(patientA, doctorCurious, "12:30");

        doctorService.showAvailableSlotsBySpecialization(DoctorSpecialization.CARDIOLOGIST);

        bookingService.bookAppointment(patientB, doctorCurious, "12:30");
        bookingService.bookAppointment(patientC, doctorCurious, "12:30");

        bookingService.showBookedAppointments();

        bookingService.cancelBooking(1);

        doctorService.showAvailableSlotsBySpecialization(DoctorSpecialization.CARDIOLOGIST);

        bookingService.showBookedAppointments();

        bookingService.bookAppointment(patientC, doctorDreadful, "13:07");

        doctorService.showAvailableSlotsBySpecialization(DoctorSpecialization.DERMATOLOGIST);

        try {
            bookingService.cancelBooking(1);
        }
        catch (BookingNotPresentException e) {
            print.printData(e.getMessage());
        }

    }
}