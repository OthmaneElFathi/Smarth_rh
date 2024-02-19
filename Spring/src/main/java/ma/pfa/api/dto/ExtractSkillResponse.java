package ma.pfa.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExtractSkillResponse {
    private List<String> extractedSkills;
}