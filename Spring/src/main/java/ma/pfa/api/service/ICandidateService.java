package ma.pfa.api.service;

import ma.pfa.api.dto.CandidateResponseDTO;
import ma.pfa.api.dto.ResponseDTO;
import ma.pfa.api.dto.ResumeDTO;
import ma.pfa.api.models.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;

import java.io.IOException;
import java.util.List;

public interface ICandidateService {
    List<Candidate> getAllCandidates();

    Candidate getCandidateById(String id);

    Candidate getCandidateByUsername(String username);

    Candidate createCandidate(Candidate candidate);


    Candidate updateCandidate(String username,Candidate candidate);

    void deleteCandidate(String id);
    List<String> extractSkills(byte[] resume);

    void deleteCandidateByUsername(String username);

    void deleteCandidateByCandidate(Candidate candidate);

    String storeResume(String username,ResumeDTO resumeFile) throws IOException;
    ResponseDTO handleResumeUpload(String username,ResumeDTO resumeDTO) throws HttpMediaTypeNotSupportedException;
    boolean isValidResumeFileType(String fileName);

    boolean applyToJobOffer(String id);

    boolean removeApplication(String id);

    Page<CandidateResponseDTO> JobOfferApplicants(int page, int size, String searchQuery, String id);

    ResponseEntity<Boolean> hasResume(String id);

    ResponseEntity<Boolean> hasProfilePicture(String username);

    void save(Candidate candidate);
}
