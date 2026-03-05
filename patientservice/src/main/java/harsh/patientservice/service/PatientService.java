package harsh.patientservice.service;

import harsh.patientservice.dto.req.PatientRequest;
import harsh.patientservice.dto.res.PatientResponse;

import java.util.List;

public interface PatientService {

    public List<PatientResponse> getPatients();

    public PatientResponse createPatient(PatientRequest patientRequest);

    public PatientResponse updatePatient(Long id, PatientRequest updatePatientRequest);

    public void deletePatient(Long id);
}
