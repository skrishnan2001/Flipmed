package com.microservices.app.services;

import com.microservices.app.display.Print;
import com.microservices.app.exceptions.BookingNotPresentException;
import com.microservices.app.exceptions.DoctorNotPresentException;
import com.microservices.app.exceptions.PatientNotPresentException;
import com.microservices.app.exceptions.SlotException;
import com.microservices.app.models.Booking;
import com.microservices.app.models.Doctor;
import com.microservices.app.models.Patient;
import com.microservices.app.models.TimeSlot;
import com.microservices.app.repositories.DoctorsRepository;
import com.microservices.app.repositories.PatientsRepository;
import com.microservices.app.utils.Utils;

import java.util.*;

public class BookingService {

    private final DoctorsRepository doctorsRepository;
    private final PatientsRepository patientsRepository;
    private final Print print;
    private final Map<Integer, Booking> bookings;
    private final Map<Integer, List<TimeSlot>> patientSlots;
    private final Queue<Booking> waitListQueue;

    private static int bookingId = 1;

    public BookingService(DoctorsRepository doctorsRepository, PatientsRepository patientsRepository, Print print) {
        this.doctorsRepository = doctorsRepository;
        this.patientsRepository = patientsRepository;
        this.print = print;
        this.patientSlots = new HashMap<>();
        this.waitListQueue = new LinkedList<>();
        this.bookings = new HashMap<>();
    }

    // Book Appointment (PatientObj, DoctorObj, startFrom)

    public void bookAppointment(Patient patient, Doctor doctor, String fromSlot) {
        // Checking if patient exists
        if(!patientsRepository.isPatientRegistered(patient.getPatientId()))
            throw new PatientNotPresentException("The patient does not exist in Flipmed!");

        //Checking if doctor exists
        if(!doctorsRepository.isDoctorRegistered(doctor.getDoctorId()))
            throw new DoctorNotPresentException("The doctor does not exist in Flipmed!");

        //Checking if patient is already booked for that slot
        if(patientSlots.containsKey(patient.getPatientId())) {
            for(TimeSlot slot : patientSlots.get(patient.getPatientId())) {
                if(slot.getStartTime().equals(fromSlot))
                    throw new SlotException("This slot is already booked for the patient!");
            }
        }

        else
            patientSlots.put(patient.getPatientId(), new ArrayList<>());

        //Checking if doctor is available for that slot
        Map<TimeSlot, Boolean> doctorAvailability = doctor.getAvailableSlots();

        for(TimeSlot slot : doctorAvailability.keySet()) {
            if(slot.getStartTime().equals(fromSlot) && doctorAvailability.get(slot)) {
                doctorAvailability.put(slot, false);
                patientSlots.get(patient.getPatientId()).add(slot);

                Booking booking = new Booking(bookingId++, doctor, patient, slot);
                bookings.put(booking.getBookingId(), booking);
                print.printData("Appointment Booked Successfully, Booking id "+ booking.getBookingId());
                return;
            }
        }

        print.printData("No free slots available at the moment, you will be waitlisted");
        Booking booking = new Booking(bookingId++, doctor, patient, Utils.createThirtyMinutesTimeSlotWithStartTime(fromSlot));

        booking.setIsWaitListed(true);
        waitListQueue.add(booking);
        print.printData("Waitlisted, Booking id: " + booking.getBookingId());
    }

    public void cancelBooking(Integer bookingId) {
        if(!bookings.containsKey(bookingId))
            throw new BookingNotPresentException("No booking available for this booking ID!");

        Booking booking = bookings.get(bookingId);

        doctorsRepository.freeSlot(booking.getDoctor().getDoctorId(), booking.getTimeSlot());
        bookings.remove(bookingId);
        print.printData("Booking cancelled!!");
        patientSlots.get(booking.getPatient().getPatientId()).remove(booking.getTimeSlot());

        createBookingFromWaitingListIfExists(booking);
    }

    private void createBookingFromWaitingListIfExists(Booking booking) {
        for(Booking waitListBooking : waitListQueue) {
            if(waitListBooking.getTimeSlot().getStartTime().equals(booking.getTimeSlot().getStartTime())) {
                waitListBooking.setIsWaitListed(false);

                Doctor doctor = waitListBooking.getDoctor();

                for(TimeSlot timeSlot : doctor.getAvailableSlots().keySet()) {
                    if(timeSlot.getStartTime().equals(booking.getTimeSlot().getStartTime())) {
                        doctor.getAvailableSlots().put(timeSlot, false);
                        break;
                    }
                }

                bookings.put(waitListBooking.getBookingId(), waitListBooking);
                waitListQueue.remove(waitListBooking);
                return;
            }
        }
    }

    public void showBookedAppointments() {
        for(Integer bookingId : bookings.keySet()) {
            Booking booking = bookings.get(bookingId);

            print.printData(bookingId + " " +
                    booking.getPatient().getPatientName() + " " +
                    booking.getDoctor().getDoctorName() + " " +
                    booking.getTimeSlot().getStartTime() + " " +
                    booking.getTimeSlot().getEndTime());
        }
    }








}
