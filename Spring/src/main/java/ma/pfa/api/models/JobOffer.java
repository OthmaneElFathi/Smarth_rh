package ma.pfa.api.models;

import lombok.*;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "job_offers")
public class JobOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String description;
    private LocalDateTime dateAdded;
    private LocalDateTime dateExpired;
    @ElementCollection
    @CollectionTable(name = "job_offer_required_skills", joinColumns = @JoinColumn(name = "job_offer_id"))
    @Column(name = "required_skill")
    private Set<String> requiredSkills = new HashSet<>();
    @ManyToMany(mappedBy = "jobOffers")
    private Set<Candidate> candidates=new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    private Recruiter recruiter;

    public JobOffer(String name, String description, LocalDateTime now, LocalDateTime expired, HashSet<String> skills, Recruiter recruiter) {
    this.name=name;
    this.description=description;
    this.dateAdded=now;
    this.dateExpired=expired;
    this.requiredSkills=skills;
    this.recruiter=recruiter;
    }
}
