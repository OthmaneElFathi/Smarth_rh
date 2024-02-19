package ma.pfa.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "resumes")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private byte[] resume;
    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
    public Resume(String title)
    {
        this.title=title;
    }
}