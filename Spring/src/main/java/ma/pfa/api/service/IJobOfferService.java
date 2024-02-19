package ma.pfa.api.service;

import ma.pfa.api.dto.JobOfferRequest;
import ma.pfa.api.dto.JobOfferResponse;
import ma.pfa.api.models.JobOffer;
import org.springframework.data.domain.Page;

public interface IJobOfferService {
    Page<JobOffer> getAllJobOffers(int page, int size, String searchQuery);
    Page<JobOffer> getRecruiterJobOffersByUsername(int page, int size, String searchQuery,String username);
    Page<JobOffer> getCandidateJobOffersByUsername(int page, int size, String searchQuery,String username);
    JobOffer getJobOfferById(String id);
    JobOfferResponse getJobOfferResponseById(String id);

    boolean createJobOffer(JobOfferRequest jobOfferRequest);

    JobOffer updateJobOffer(String id, JobOfferRequest jobOffer);

    void deleteJobOffer(String id);
}
