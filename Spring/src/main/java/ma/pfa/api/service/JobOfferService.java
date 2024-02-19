package ma.pfa.api.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ma.pfa.api.dto.JobOfferRequest;
import ma.pfa.api.dto.JobOfferResponse;
import ma.pfa.api.models.Candidate;
import ma.pfa.api.models.JobOffer;
import ma.pfa.api.models.Recruiter;
import ma.pfa.api.repository.CandidateRepository;
import ma.pfa.api.repository.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class JobOfferService implements IJobOfferService {
    @PersistenceContext
    private EntityManager entityManager;
    private final JobOfferRepository jobOfferRepository;
    private final CandidateRepository candidateRepository;
    private final IRecruiterService recruiterService;

    @Autowired
    public JobOfferService(CandidateRepository candidateRepository,JobOfferRepository jobOfferRepository, IRecruiterService recruiterService) {
        this.jobOfferRepository = jobOfferRepository;
        this.recruiterService = recruiterService;
        this.candidateRepository = candidateRepository;
    }

    @Override
    public Page<JobOffer> getAllJobOffers(int page, int size, String searchQuery) {
        Pageable pageable = PageRequest.of(page, size);
        if (searchQuery != null && !searchQuery.isEmpty()) {
            return jobOfferRepository.findAllByNameContainingIgnoreCase(searchQuery, pageable);
        } else {

            return jobOfferRepository.findAll(pageable);
        }
    }

    @Override
    public Page<JobOffer> getRecruiterJobOffersByUsername(int page, int size, String searchQuery, String username) {
        Pageable pageable = PageRequest.of(page, size);
        if (searchQuery != null && !searchQuery.isEmpty()) {
            return jobOfferRepository.findAllByRecruiter_UsernameAndNameContainingIgnoreCase(searchQuery, pageable,username);
        } else {

            return jobOfferRepository.findAllByRecruiter_Username(pageable,username);
        }
    }
    @Override
    public Page<JobOffer> getCandidateJobOffersByUsername(int page, int size, String searchQuery, String username) {
        Pageable pageable = PageRequest.of(page, size);
        Candidate candidate = candidateRepository.findByUsername(username).get();
        if (searchQuery != null && !searchQuery.isEmpty()) {
            return jobOfferRepository.findAllByCandidatesContainsAndNameContainingIgnoreCase(searchQuery, pageable,candidate);
        } else {

            return jobOfferRepository.findAllByCandidatesContains(pageable,candidate);
        }
    }


    @Override
    public JobOffer getJobOfferById(String id) {
        return jobOfferRepository.findById(id).orElse(null);
    }

    @Override
    public JobOfferResponse getJobOfferResponseById(String id) {
        JobOffer j = jobOfferRepository.findById(id).orElse(null);
        JobOfferResponse jobOffer = JobOfferResponse.builder().requiredSkills(j.getRequiredSkills()).id(j.getId()).name(j.getName()).dateAdded(j.getDateAdded()).dateExpired(j.getDateExpired()).description(j.getDescription()).recruiter(j.getRecruiter().toDTO()).build();
        return jobOffer;
    }

    @Override
    public boolean createJobOffer(JobOfferRequest jobOfferRequest) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String currentRecruiterUsername = userDetails.getUsername();
            Recruiter recruiter = recruiterService.getRecruiterByUsername(currentRecruiterUsername);
            JobOffer jobOffer = new JobOffer(
                    jobOfferRequest.getName(),
                    jobOfferRequest.getDescription(),
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(jobOfferRequest.getDaysUntilExpired()),
                    new HashSet<>(jobOfferRequest.getRequiredSkills()),
                    recruiter
            );
            jobOfferRepository.save(jobOffer);
            return true;
        } catch (AccessDeniedException e) {
            System.out.println("Access denied: " + e.getMessage());
            return false;
        }
    }
    @Override
    public JobOffer updateJobOffer(String id, JobOfferRequest jobOffer) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentRecruiterUsername = userDetails.getUsername();
        JobOffer existingJobOffer = getJobOfferById(id);
        if (!currentRecruiterUsername.equals(existingJobOffer.getRecruiter().getUsername())) {
            throw new AccessDeniedException("You are not authorized to update this job offer.");
        }
        JobOffer j = JobOffer.builder()
                .id(id)
                .name(jobOffer.getName())
                .description(jobOffer.getDescription())
                .dateAdded(LocalDateTime.now())
                .dateExpired(LocalDateTime.now().plusDays(jobOffer.getDaysUntilExpired()))
                .requiredSkills(new HashSet<>(jobOffer.getRequiredSkills()))
                .recruiter(existingJobOffer.getRecruiter())
                .build();
        return jobOfferRepository.save(j);
    }
    @Override
    public void deleteJobOffer(String id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentRecruiterUsername = userDetails.getUsername();
        JobOffer existingJobOffer = getJobOfferById(id);
        if (!currentRecruiterUsername.equals(existingJobOffer.getRecruiter().getUsername())) {
            throw new AccessDeniedException("You are not authorized to delete this job offer.");
        }
        Set<Candidate> candidates = existingJobOffer.getCandidates();
        for (Candidate candidate : candidates) {
            Candidate newC = candidate.removeJobOffer(existingJobOffer);
            candidateRepository.save(newC);
        }
        jobOfferRepository.deleteById(id);
    }
}
