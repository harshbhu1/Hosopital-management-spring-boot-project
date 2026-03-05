package harsh.patientservice.dto.res;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientResponse {

    private Long id;
    private String name;
    private String email;
    private String address;
    private String dateOfBirth;
}
