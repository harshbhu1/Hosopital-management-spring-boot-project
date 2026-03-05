package harsh.patientservice.utils;

import harsh.patientservice.dto.req.PatientRequest;
import harsh.patientservice.dto.res.PatientResponse;
import harsh.patientservice.model.Patient;

import java.time.LocalDate;

public class Mapper {

    /**
     *
     * Mapper method mapped patient request to patient model.
     */
    public static Patient mapToPatient(PatientRequest patientRequest) {
        Patient patient = new Patient();
        patient.setName(patientRequest.getName());
        patient.setEmail(patientRequest.getEmail());
        patient.setAddress(patientRequest.getAddress());
        patient.setRegisteredDate(LocalDate.parse(patientRequest.getRegisteredDate()));
        patient.setDateOfBirth(LocalDate.parse(patientRequest.getDateOfBirth()));
        return  patient;
    }

    /**
     * Mapper method mapped patient model to patientResponse method.
     */
    public static PatientResponse toPatientResponse(Patient patient) {
        return PatientResponse.builder()
                .id(patient.getId()).
                name(patient.getName())
                .email(patient.getEmail())
                .address(patient.getAddress())
                .dateOfBirth(patient.getDateOfBirth().toString())
                .build();
    }
}
