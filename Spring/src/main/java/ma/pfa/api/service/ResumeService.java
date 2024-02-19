package ma.pfa.api.service;

import ma.pfa.api.models.Candidate;
import ma.pfa.api.models.Resume;
import ma.pfa.api.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ResumeService implements IResumeService{

    @Autowired
    private ResumeRepository resumeRepo;
    @Override
    public Resume addResume(String title, Candidate candidate , MultipartFile file) throws IOException {
        Resume resume = new Resume(title);
        resume.setResume(
                file.getBytes());
        resume.setCandidate(candidate);
        return resumeRepo.save(resume);
    }
    @Override
    public Resume getResume(String candidateUsername) {
        return resumeRepo.findByCandidate_Username(candidateUsername).get();
    }

    @Override
    public void deleteById(String id) {
        resumeRepo.deleteById(id);
    }
}