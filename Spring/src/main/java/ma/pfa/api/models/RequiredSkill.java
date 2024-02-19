package ma.pfa.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "required_skills")
public class RequiredSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String skillName;

    @ManyToOne
    @JoinColumn(name = "job_offer_id")
    private JobOffer jobOffer;

    public RequiredSkill(String skillName) {
        this.skillName=skillName;
    }
}