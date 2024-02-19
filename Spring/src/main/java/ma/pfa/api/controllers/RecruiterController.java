package ma.pfa.api.controllers;

import ma.pfa.api.models.Recruiter;
import ma.pfa.api.service.IRecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruiters")
public class RecruiterController {

    private final IRecruiterService recruiterService;

    @Autowired
    public RecruiterController(IRecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }

    @GetMapping
    public List<Recruiter> getAllRecruiters() {
        return recruiterService.getAllRecruiters();
    }

    @GetMapping("/{id}")
    public Recruiter getRecruiterById(@PathVariable String id) {
        return recruiterService.getRecruiterById(id);
    }

    @PostMapping
    public Recruiter createRecruiter(@RequestBody Recruiter recruiter) {
        return recruiterService.createRecruiter(recruiter);
    }

    @PutMapping("/{id}")
    public Recruiter updateRecruiter(@PathVariable String id, @RequestBody Recruiter recruiter) {
        return recruiterService.updateRecruiter(id, recruiter);
    }

    @DeleteMapping("/{id}")
    public void deleteRecruiter(@PathVariable String id) {
        recruiterService.deleteRecruiter(id);
    }
}
