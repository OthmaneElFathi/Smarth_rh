package ma.pfa.api.repository;

import ma.pfa.api.models.Candidate;
import ma.pfa.api.models.JobOffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobOfferRepository extends JpaRepository<JobOffer, String> {
    Page<JobOffer> findAllByNameContainingIgnoreCase(String searchQuery, Pageable pageable);

    Page<JobOffer> findAllByRecruiter_Username(Pageable pageable,String username);
    Page<JobOffer> findAllByRecruiter_UsernameAndNameContainingIgnoreCase(String searchQuery,Pageable pageable,String username);
    Page<JobOffer> findAllByCandidatesContains(Pageable pageable,Candidate candidate);
    Page<JobOffer> findAllByCandidatesContainsAndNameContainingIgnoreCase(String searchQuery,Pageable pageable,Candidate candidate);

}
