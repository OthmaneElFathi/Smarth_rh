package ma.pfa.api.dto;

import lombok.Builder;
import lombok.Data;
import ma.pfa.api.models.ProfilePicture;
import ma.pfa.api.models.Resume;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CandidateResponseDTO implements Serializable {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String description;
    private List<String> skills = new ArrayList<>();
}
