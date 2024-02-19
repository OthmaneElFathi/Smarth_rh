package ma.pfa.api.repository;

import ma.pfa.api.models.Candidate;
import ma.pfa.api.models.JobOffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, String> {
    Optional<Candidate> findByUsername(String candidateId);
    Optional<Candidate> findById(String id);
    Page<Candidate> findAllByJobOffersContains(Pageable pageable, JobOffer jobOffer);
}
