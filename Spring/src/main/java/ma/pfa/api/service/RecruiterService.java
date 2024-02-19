package ma.pfa.api.service;

import ma.pfa.api.models.Recruiter;
import ma.pfa.api.repository.RecruiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecruiterService implements IRecruiterService {

    private final RecruiterRepository recruiterRepository;

    @Autowired
    public RecruiterService(RecruiterRepository recruiterRepository) {
        this.recruiterRepository = recruiterRepository;
    }

    @Override
    public List<Recruiter> getAllRecruiters() {
        return recruiterRepository.findAll();
    }

    @Override
    public Recruiter getRecruiterById(String id) {
        return recruiterRepository.findById(id).orElse(null);
    }

    @Override
    public Recruiter getRecruiterByUsername(String username) {
        return recruiterRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Recruiter createRecruiter(Recruiter recruiter) {
        return recruiterRepository.save(recruiter);
    }

    @Override
    public Recruiter updateRecruiter(String id, Recruiter recruiter) {
        Optional<Recruiter> existingRecruiter = recruiterRepository.findById(id);
        if (existingRecruiter.isPresent()) {
            recruiter.setId(id);
            return recruiterRepository.save(recruiter);
        }
        return null;
    }

    @Override
    public void deleteRecruiter(String id) {
        recruiterRepository.deleteById(id);
    }
}
