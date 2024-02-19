package ma.pfa.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecruiterDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String companyName;
    private String contactNumber;
    private String companyAddress;
}
