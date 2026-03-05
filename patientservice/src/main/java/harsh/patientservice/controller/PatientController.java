package harsh.patientservice.controller;

import harsh.patientservice.dto.req.PatientRequest;
import harsh.patientservice.dto.res.PatientResponse;
import harsh.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Patient" , description = "Apis for Managing Patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Controller to get all the patients.
     */
    @GetMapping
    @Operation(summary = "Get Patients")
    public ResponseEntity<List<PatientResponse>> getPatients() {
        return ResponseEntity.ok().body(patientService.getPatients());
    }

    /**
     * Controller to register the patient.
     */
    @PostMapping("/register-patient")
    @Operation(summary = "Register Patient")
    public ResponseEntity<PatientResponse> registerPatient( @Valid @RequestBody PatientRequest patientRequest) {
        return ResponseEntity.ok(patientService.createPatient(patientRequest));
    }

    /**
     * Controller to update the patient.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update Patient")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable Long id ,
                                                         @RequestBody PatientRequest patientRequest) {
        return ResponseEntity.ok(patientService.updatePatient(id,patientRequest));
    }

    /**
     * Controller to delete the patient.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Patient")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok().build();
    }

}
