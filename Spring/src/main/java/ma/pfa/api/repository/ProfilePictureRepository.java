package ma.pfa.api.repository;

import ma.pfa.api.models.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, String> {
    Optional<ProfilePicture> findByUserId(String id);
    Optional<ProfilePicture> findByUserUsername(String username);

}