package ma.pfa.api.controllers;

import ma.pfa.api.dto.CandidateResponseDTO;
import ma.pfa.api.dto.ResponseDTO;
import ma.pfa.api.dto.ResumeDTO;
import ma.pfa.api.models.Candidate;
import ma.pfa.api.models.ProfilePicture;
import ma.pfa.api.models.Resume;
import ma.pfa.api.service.ICandidateService;

import ma.pfa.api.service.IProfilePictureService;
import ma.pfa.api.service.IResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/candidates")
@PreAuthorize("hasAnyRole('ROLE_RECRUITER','ROLE_CANDIDATE')")
public class CandidateController {
    private final ICandidateService candidateService;
    private final IProfilePictureService profilePictureService;


    private final IResumeService resumeService;

    @Autowired
    public CandidateController(IProfilePictureService profilePictureService, IResumeService resumeService, ICandidateService candidateService) {
        this.candidateService = candidateService;
        this.resumeService = resumeService;
        this.profilePictureService = profilePictureService;
    }

    @GetMapping
    public List<CandidateResponseDTO> getAllCandidates() {
        List<Candidate> candidates = candidateService.getAllCandidates();
        return candidates.stream()
                .map(candidate -> CandidateResponseDTO.builder()
                        .skills(candidate.getSkills()).description(candidate.getDescription()).username(candidate.getUsername()).email(candidate.getEmail()).firstName(candidate.getFirstName()).lastName(candidate.getLastName()).build())
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponseDTO> getCandidateById(@PathVariable String id) {
        Candidate candidate = candidateService.getCandidateById(id);
        CandidateResponseDTO candidateResponse = CandidateResponseDTO.builder().description(candidate.getDescription()).skills(candidate.getSkills()).username(candidate.getUsername()).email(candidate.getEmail()).firstName(candidate.getFirstName()).lastName(candidate.getLastName()).build();
        return new ResponseEntity<>(candidateResponse, HttpStatus.OK);
    }

    @PostMapping
    public Candidate createCandidate(@RequestBody Candidate candidate) {
        return candidateService.createCandidate(candidate);
    }

    @GetMapping("/resume/{username}")
    public ResponseEntity<Boolean> hasResume(@PathVariable String username) {
        return candidateService.hasResume(username);
    }

    @GetMapping("/profile-picture/{username}")
    public ResponseEntity<Boolean> hasProfilePicture(@PathVariable String username) {
        return candidateService.hasProfilePicture(username);
    }

    @GetMapping("/joboffers/{id}")
    public Page<CandidateResponseDTO> JobOfferApplicants(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @RequestParam(required = false) String searchQuery, @PathVariable String id) {
        return candidateService.JobOfferApplicants(page, size, searchQuery, id);
    }

    @PutMapping("/apply/{id}")
    public ResponseEntity<Boolean> applyToJobOffer(@PathVariable String id) {
        return new ResponseEntity<>(candidateService.applyToJobOffer(id), HttpStatus.OK);
    }

    @DeleteMapping("/remove-application/{id}")
    public ResponseEntity<Boolean> removeApplicationToJobOffer(@PathVariable String id) {
        return new ResponseEntity<>(candidateService.removeApplication(id), HttpStatus.OK);
    }

    @PutMapping("/update/{username}")
    public Candidate updateCandidate(@RequestBody Candidate candidate, @PathVariable String username) {
        return candidateService.updateCandidate(username, candidate);
    }

    @DeleteMapping("/{id}")
    public void deleteCandidate(@PathVariable String id) {
        candidateService.deleteCandidate(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_CANDIDATE')")
    @DeleteMapping("/username/{username}")
    public void deleteCandidateByUsername(@PathVariable String username) {
        candidateService.deleteCandidateByUsername(username);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<CandidateResponseDTO> getCandidateByUsername(@PathVariable String username) {
        Candidate candidate = candidateService.getCandidateByUsername(username);
        CandidateResponseDTO candidateResponse = CandidateResponseDTO.builder().description(candidate.getDescription()).skills(candidate.getSkills()).username(candidate.getUsername()).email(candidate.getEmail()).firstName(candidate.getFirstName()).lastName(candidate.getLastName()).build();
        return new ResponseEntity<>(candidateResponse, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_CANDIDATE')")
    @RequestMapping(value = "/upload-resume/{username}", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<ResponseDTO> handleResumeUpload(@ModelAttribute ResumeDTO resumeDTO, @PathVariable String username) throws HttpMediaTypeNotSupportedException {
        ResponseDTO responseDTO = candidateService.handleResumeUpload(username, resumeDTO);
        return new ResponseEntity<>(responseDTO, responseDTO.getStatus());
    }

    @GetMapping("/download/{candidateUsername}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String candidateUsername) {
        Resume pdfFile = resumeService.getResume(candidateUsername);
        if (pdfFile != null) {
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + pdfFile.getTitle() + "\"")
                    .body(pdfFile.getResume());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/picture/{username}")
    public ResponseEntity<byte[]> getProfilePictureByUsername(@PathVariable String username) {
        ProfilePicture profilePicture = profilePictureService.getProfilePictureByUsername(username);
        if (profilePicture != null && profilePicture.getImage() != null) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)                     .body(profilePicture.getImage());
        } else {
            byte[] defaultPictureBytes = profilePictureService.getDefaultProfilePicture();
                        if (defaultPictureBytes != null) {
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(defaultPictureBytes);
            } else {
                                return ResponseEntity.notFound().build();
            }
        }
    }
}
