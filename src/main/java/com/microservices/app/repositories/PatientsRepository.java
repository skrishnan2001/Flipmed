package com.microservices.app.repositories;

import com.microservices.app.exceptions.PatientAlreadyPresentException;
import com.microservices.app.models.Patient;

import java.util.HashMap;
import java.util.Map;

public class PatientsRepository {

    private final Map<Integer, Patient> patients;

    public PatientsRepository() {
        patients = new HashMap<>();
    }

    public void registerPatient(Patient patient) {
        if(patients.containsKey(patient.getPatientId()))
            throw new PatientAlreadyPresentException("This patient already exists in the system !!");

        patients.put(patient.getPatientId(), patient);
    }

    public boolean isPatientRegistered(Integer patientId) {
        return patients.containsKey(patientId);
    }

}
