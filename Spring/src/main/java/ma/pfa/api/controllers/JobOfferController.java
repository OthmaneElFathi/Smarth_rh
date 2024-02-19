package ma.pfa.api.controllers;
import ma.pfa.api.dto.JobOfferRequest;
import ma.pfa.api.dto.JobOfferResponse;
import ma.pfa.api.models.JobOffer;
import ma.pfa.api.service.IJobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/joboffers")
@PreAuthorize("hasAnyRole('ROLE_RECRUITER','ROLE_CANDIDATE')")
public class JobOfferController {
    private final IJobOfferService jobOfferService;

    @Autowired
    public JobOfferController(IJobOfferService jobOfferService) {
        this.jobOfferService = jobOfferService;
    }

    @GetMapping
    public Page<JobOfferResponse> getAllJobOffers(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(required = false) String searchQuery) {
        Page<JobOffer> jobOffersPage = jobOfferService.getAllJobOffers(page, size,searchQuery);
        return jobOffersPage.map(jobOffer -> JobOfferResponse.builder().id(jobOffer.getId())
                .name(jobOffer.getName()).requiredSkills(jobOffer.getRequiredSkills())
                .description(jobOffer.getDescription()).dateAdded(jobOffer.getDateAdded())
                .dateExpired(jobOffer.getDateExpired()).recruiter(jobOffer.getRecruiter().toDTO()).build());
    }
    @GetMapping("/recruiter/{username}")
    public Page<JobOfferResponse> getRecruiterJobOffers(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(required = false) String searchQuery,@PathVariable String username) {
        Page<JobOffer> jobOffersPage = jobOfferService.getRecruiterJobOffersByUsername(page, size,searchQuery,username);
        return jobOffersPage.map(jobOffer -> JobOfferResponse.builder().id(jobOffer.getId())
                .name(jobOffer.getName()).requiredSkills(jobOffer.getRequiredSkills())
                .description(jobOffer.getDescription()).dateAdded(jobOffer.getDateAdded())
                .dateExpired(jobOffer.getDateExpired()).recruiter(jobOffer.getRecruiter().toDTO()).build());
    }
    @GetMapping("/candidate/{username}")
    public Page<JobOfferResponse> geCandidateJobOffers(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(required = false) String searchQuery,@PathVariable String username) {
        Page<JobOffer> jobOffersPage = jobOfferService.getCandidateJobOffersByUsername(page, size,searchQuery,username);
        return jobOffersPage.map(jobOffer -> JobOfferResponse.builder().id(jobOffer.getId())
                .name(jobOffer.getName()).requiredSkills(jobOffer.getRequiredSkills())
                .description(jobOffer.getDescription()).dateAdded(jobOffer.getDateAdded())
                .dateExpired(jobOffer.getDateExpired()).recruiter(jobOffer.getRecruiter().toDTO()).build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<JobOfferResponse> getJobOfferById(@PathVariable String id) {
        return new ResponseEntity<>(jobOfferService.getJobOfferResponseById(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_RECRUITER')")
    public boolean createJobOffer(@RequestBody JobOfferRequest jobOfferRequest) {
        return jobOfferService.createJobOffer(jobOfferRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_RECRUITER')")
    public boolean updateJobOffer(@PathVariable String id, @RequestBody JobOfferRequest jobOffer) {
        jobOfferService.updateJobOffer(id, jobOffer);
        return true;
    }

    @DeleteMapping("/{id}")
    public void deleteJobOffer(@PathVariable String id) {
        jobOfferService.deleteJobOffer(id);
    }
}
