package ma.pfa.api.repository;

import ma.pfa.api.models.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, String> {
    Optional<Resume> findByCandidate_Username(String username);
}