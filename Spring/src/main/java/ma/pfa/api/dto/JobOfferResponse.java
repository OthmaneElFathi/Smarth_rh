package ma.pfa.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class JobOfferResponse {
    private String id;
    private String name;
    private String description;
    private LocalDateTime dateAdded;
    private LocalDateTime dateExpired;
    private Set<String> requiredSkills = new HashSet<>();
    private RecruiterDTO recruiter;


}
