package ma.pfa.api.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "candidates")
public class Candidate extends UserEntity {
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Resume> resumes = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "candidate_skills", joinColumns = @JoinColumn(name = "candidate_id"))
    @Column(name = "skill")
    private List<String> skills = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "candidate_job_offer",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "job_offer_id")
    )
    private Set<JobOffer> jobOffers = new HashSet<>();
    private String description;


    public void addResume(Resume resume)
    {
        this.resumes.add(resume);
    }
    public Candidate addJobOffer(JobOffer jobOffer)
    {
        this.jobOffers.add(jobOffer);
        return this;
    }
    public void addSkills(List<String> skills)
    {
        this.skills=skills;
    }

    public Candidate removeJobOffer(JobOffer jobOffer) {
        this.jobOffers.remove(jobOffer);
        return this;
    }
}
