package ma.pfa.api.service;

import ma.pfa.api.models.ProfilePicture;
import ma.pfa.api.models.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IProfilePictureService {
    ProfilePicture addProfilePicture(UserEntity user, MultipartFile file) throws IOException;

    ProfilePicture getProfilePicture(String id);

    void deleteById(String id);

    ProfilePicture getProfilePictureByUserId(String id);

    ProfilePicture getProfilePictureByUsername(String username);

    byte[] getDefaultProfilePicture();
}