package ma.pfa.api.dto;

import lombok.Data;

import java.util.*;

@Data
public class JobOfferRequest {
    private String name;
    private String description;
    private int daysUntilExpired;
    private List<String> requiredSkills = new ArrayList<>();
}
