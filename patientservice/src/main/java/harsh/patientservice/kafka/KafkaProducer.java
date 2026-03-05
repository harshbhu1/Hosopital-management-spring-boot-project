package harsh.patientservice.kafka;

import harsh.patientservice.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Slf4j
@Service
public class KafkaProducer {
    public static final String PATIENT_KAFKA_TOPIC = "patient";
    private final KafkaTemplate<String,byte[]> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient){
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("Patient Created")
                .build();
        try{
            kafkaTemplate.send(PATIENT_KAFKA_TOPIC,
                    patient.getId().toString(), event.toByteArray());
        }catch (Exception e){
            log.error("Error while sending event {}",event);
        }
    }
}
