package ma.pfa.api.service;

import ma.pfa.api.dto.CandidateResponseDTO;
import ma.pfa.api.dto.ResponseDTO;
import ma.pfa.api.dto.ResumeDTO;
import ma.pfa.api.models.*;
import ma.pfa.api.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class CandidateService implements ICandidateService {
    private final CandidateRepository candidateRepository;
    private final IResumeService resumeService;
    private final IAuthService authService;
    private final IProfilePictureService profilePictureService;
private final IJobOfferService jobOfferService;
    private static String EXTERNAL_API_URL ;

    @Autowired
    public CandidateService(@Value("${flask.url}") String flaskUrl,IJobOfferService jobOfferService,IAuthService authService,IProfilePictureService profilePictureService,ResumeService resumeService,CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
        this.resumeService=resumeService;
        this.profilePictureService=profilePictureService;
        this.authService=authService;
        this.jobOfferService=jobOfferService;
        EXTERNAL_API_URL = flaskUrl + "/extract_skills";
    }
    @Override
    public List<Candidate> getAllCandidates() {
        try {
            return candidateRepository.findAll();
        }catch(AccessDeniedException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Candidate getCandidateById(String id) {
        return candidateRepository.findById(id).orElse(null);
    }

    @Override
    public Candidate getCandidateByUsername(String username) {
        return candidateRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Candidate createCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    public Candidate updateCandidate(String username,Candidate candidate) {
        Candidate existingCandidate = candidateRepository.findByUsername(username).orElse(null);
        if (existingCandidate!=null) {
            candidate.setId(existingCandidate.getId());
            return candidateRepository.save(candidate);
        }
        return null;
    }


    @Override
    public List<String> extractSkills(byte[] resume) throws ResourceAccessException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(resume));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<Map<String, List<String>>> responseEntity = restTemplate.exchange(
                    EXTERNAL_API_URL,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<>() {}
            );

            Map<String, List<String>> responseBody = responseEntity.getBody();
            if (responseBody != null && responseBody.containsKey("extracted_skills")) {
                return responseBody.get("extracted_skills");
            } else {
                System.err.println("Unexpected response format");
                return Collections.emptyList();
            }
        } catch (HttpClientErrorException.BadRequest e) {
            System.err.println("Bad Request: " + e.getMessage());
            return Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    @Override
    public void deleteCandidateByUsername(String username) {
        Candidate candidate = candidateRepository.findByUsername(username).orElse(null);
        if (candidate != null) {
            deleteCandidateByCandidate(candidate);
        }
    }
    @Override
    public void deleteCandidate(String id) {
        Candidate candidate = candidateRepository.findById(id).orElse(null);
        deleteCandidateByCandidate(candidate);
    }
    @Override
    public void deleteCandidateByCandidate(Candidate candidate) {
        if (candidate != null) {
            candidate.getResumes().forEach(resume -> {
                resumeService.deleteById(resume.getId());
            });
            ProfilePicture profilePicture = candidate.getProfilePicture();
            if (profilePicture != null) {
                profilePictureService.deleteById(profilePicture.getId());
            }
            candidateRepository.deleteById(candidate.getId());
        }
    }
    @Override
    public String storeResume(String username,ResumeDTO resumeFile) throws IOException {
        Candidate candidate = candidateRepository.findByUsername(username).orElse(null);
        if (candidate == null) {
            throw new IllegalArgumentException("Candidate not found with username: " + username);
        }
        List<String> skills = extractSkills(resumeFile.getResume().getBytes());
        Resume resume=new Resume();
        if(skills!=null) {
            resume = resumeService.addResume(resumeFile.getTitle(),candidate,resumeFile.getResume());
            candidate.addResume(resume);
            candidate.addSkills(skills);
            candidateRepository.save(candidate);
        }
        return resume.getId();
    }
    @Override
    public ResponseDTO handleResumeUpload(String username,ResumeDTO resumeDTO) {
        try {
                if (!isValidResumeFileType(resumeDTO.getResume().getOriginalFilename())) {
                    return new ResponseDTO("invalid file type",HttpStatus.BAD_REQUEST);
                }
                return new ResponseDTO("resume succesfully uploaded",HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO("error in resume upload",HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public boolean isValidResumeFileType(String fileName) {
        return fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".doc") || fileName.toLowerCase().endsWith(".docx");
    }

    @Override
    public boolean applyToJobOffer(String id) {
        Candidate candidate = getCandidateById(authService.getCurrentUser().getId());
        JobOffer jobOffer = jobOfferService.getJobOfferById(id);
        Candidate newCandidate = candidate.addJobOffer(jobOffer);
        candidateRepository.save(newCandidate);
        return true;
    }

    @Override
    public boolean removeApplication(String id) {
        Candidate candidate = getCandidateById(authService.getCurrentUser().getId());
        JobOffer jobOffer = jobOfferService.getJobOfferById(id);
        Candidate newCandidate = candidate.removeJobOffer(jobOffer);
        candidateRepository.save(newCandidate);
        return true;
    }
    @Override
    public Page<CandidateResponseDTO> JobOfferApplicants(int page, int size, String searchQuery,String id) {
        Pageable pageable = PageRequest.of(page, size);
        JobOffer jobOffer = jobOfferService.getJobOfferById(id);
        Page<Candidate> candidatePage = candidateRepository.findAllByJobOffersContains(pageable,jobOffer );
        return candidatePage.map(candidate -> CandidateResponseDTO.builder()
                .id(candidate.getId())
                .username(candidate.getUsername())
                .description(candidate.getDescription())
                .email(candidate.getEmail())
                .lastName(candidate.getLastName())
                .firstName(candidate.getFirstName())
                .skills(candidate.getSkills())                .build());
    }

    @Override
    public ResponseEntity<Boolean> hasResume(String username) {
        Candidate candidate = candidateRepository.findByUsername(username).get();
        if(candidate.getResumes().size()>0)
            return new ResponseEntity<>(true,HttpStatus.OK);
        return new ResponseEntity<>(false,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Boolean> hasProfilePicture(String username) {
        Candidate candidate = candidateRepository.findByUsername(username).get();
        if(candidate.getProfilePicture()!=null)
            return new ResponseEntity<>(true,HttpStatus.OK);
        return new ResponseEntity<>(false,HttpStatus.OK);
    }

    @Override
    public void save(Candidate candidate) {
        candidateRepository.save(candidate);
    }
}

