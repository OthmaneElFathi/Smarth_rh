package ma.pfa.api.service;

import ma.pfa.api.models.Recruiter;

import java.util.List;

public interface IRecruiterService {
    List<Recruiter> getAllRecruiters();

    Recruiter getRecruiterById(String id);

    Recruiter getRecruiterByUsername(String username);

    Recruiter createRecruiter(Recruiter recruiter);

    Recruiter updateRecruiter(String id, Recruiter recruiter);

    void deleteRecruiter(String id);
}
