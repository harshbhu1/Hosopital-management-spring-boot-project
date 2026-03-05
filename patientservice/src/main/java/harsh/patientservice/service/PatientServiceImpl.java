package harsh.patientservice.service;

import harsh.patientservice.dto.req.PatientRequest;
import harsh.patientservice.dto.res.PatientResponse;
import harsh.patientservice.grpc.BillingServiceGrpcClient;
import harsh.patientservice.kafka.KafkaProducer;
import harsh.patientservice.model.Patient;
import harsh.patientservice.repository.PatientRepository;
import harsh.patientservice.utils.Mapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class PatientServiceImpl implements PatientService{

    private  final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public PatientServiceImpl(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient, KafkaProducer kafkaProducer) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }


    /**
     * @return this method returns all the patients info
     */
    @Override
    public List<PatientResponse> getPatients() {
        List<Patient> patients= patientRepository.findAll();
      // handle exception here for no data exists
       return  patients.stream()
               .map(Mapper::toPatientResponse)
               .toList();
    }

    /**
     * @param patientRequest  this method implements the logic of addition of the patient
     * @return it returns the newly created patient info
     */
    @Override
    @Transactional
    public PatientResponse createPatient(PatientRequest patientRequest) {
        //  handle this exception
        if(patientRepository.existsByEmail(patientRequest.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        Patient createdPatient = patientRepository.save(Mapper.mapToPatient(patientRequest));
        billingServiceGrpcClient.createBillingAccount(createdPatient.getId().toString(),
                                                        createdPatient.getName(),
                                                        createdPatient.getEmail());
        log.info(createdPatient.getName()+" has been created");
        kafkaProducer.sendEvent(createdPatient);
        log.info("Kafka Producer sent Message successfully");

        return Mapper.toPatientResponse(createdPatient);
    }

    /**
     * @param id to fetch the patient
     * @return updated patient information
     */
    @Override
    public PatientResponse updatePatient(Long id,
                                            PatientRequest patientRequest) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Patient not found with ID: " + id));

        if (patientRepository.existsByEmailAndIdNot(patientRequest.getEmail(),
                id)) {
            throw new RuntimeException(
                    "A patient with this email " + "already exists"
                            + patientRequest.getEmail());
        }
        patient.setName(patientRequest.getName());
        patient.setAddress(patientRequest.getAddress());
        patient.setEmail(patientRequest.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequest.getDateOfBirth()));
        Patient updatedPatient = patientRepository.save(patient);
        return Mapper.toPatientResponse(updatedPatient);
    }

    /**
     * @param id of the patient for that we want to delete that information
     */
    public  void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

}
