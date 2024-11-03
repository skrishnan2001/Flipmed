package com.microservices.app.services;

import com.microservices.app.display.Print;
import com.microservices.app.models.Patient;
import com.microservices.app.repositories.PatientsRepository;

public class PatientService {

    private final PatientsRepository patientsRepository;
    private final Print print;

    public PatientService(PatientsRepository patientsRepository, Print print) {
        this.patientsRepository = patientsRepository;
        this.print = print;
    }

    public void registerPatient(Patient patient) {
        patientsRepository.registerPatient(patient);
        print.printData(patient.getPatientName() + " registered successfully!");
    }
}
