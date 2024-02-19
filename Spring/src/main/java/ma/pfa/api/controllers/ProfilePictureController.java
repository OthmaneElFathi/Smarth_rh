package ma.pfa.api.controllers;

import ma.pfa.api.models.ProfilePicture;
import ma.pfa.api.service.IProfilePictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile_pictures")
public class ProfilePictureController {

    private final IProfilePictureService profilePictureService;

    @Autowired
    public ProfilePictureController(IProfilePictureService profilePictureService) {
        this.profilePictureService = profilePictureService;
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<byte[]> getProfilePictureByUsername(@PathVariable String username) {
        ProfilePicture profilePicture = profilePictureService.getProfilePictureByUsername(username);
        if (profilePicture != null && profilePicture.getImage() != null) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)                     .body(profilePicture.getImage());
        } else {
                        return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/username/{id}")
    public ResponseEntity<byte[]> getProfilePictureByUserId(@PathVariable String id) {
        ProfilePicture profilePicture = profilePictureService.getProfilePictureByUserId(id);
        if (profilePicture != null && profilePicture.getImage() != null) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)                     .body(profilePicture.getImage());
        } else {
                        return ResponseEntity.notFound().build();
        }
    }
}
