package ma.pfa.api.service;

import ma.pfa.api.models.ProfilePicture;
import ma.pfa.api.models.UserEntity;
import ma.pfa.api.repository.ProfilePictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ProfilePictureService implements IProfilePictureService {

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    @Override
    public ProfilePicture addProfilePicture(UserEntity user, MultipartFile file) throws IOException {
        ProfilePicture profilePicture = new ProfilePicture();
        profilePicture.setName(file.getOriginalFilename());
        profilePicture.setImage(file.getBytes());
        profilePicture.setUser(user);
        return profilePictureRepository.save(profilePicture);
    }

    @Override
    public ProfilePicture getProfilePicture(String id) {
        return profilePictureRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(String id) {
        profilePictureRepository.deleteById(id);
    }

    @Override
    public ProfilePicture getProfilePictureByUserId(String id) {
        return profilePictureRepository.findByUserId(id).orElse(null);
    }

    @Override
    public ProfilePicture getProfilePictureByUsername(String username) {
        return profilePictureRepository.findByUserUsername(username).orElse(null);
    }

    @Override
    public byte[] getDefaultProfilePicture() {
        try {
                        InputStream inputStream = getClass().getResourceAsStream("/default.png");
            if (inputStream != null) {
                return StreamUtils.copyToByteArray(inputStream);
            } else {
                                                return null;
            }
        } catch (IOException e) {
                        e.printStackTrace();
                        return null;
        }
    }

}
