package ma.pfa.api.service;

import ma.pfa.api.models.Candidate;
import ma.pfa.api.models.Resume;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IResumeService {


    public Resume addResume(String title, Candidate candidate, MultipartFile file) throws IOException;

    public Resume getResume(String candidateUsername);

    void deleteById(String id);
}