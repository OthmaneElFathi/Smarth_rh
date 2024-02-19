package ma.pfa.api.controllers;


import ma.pfa.api.dto.ProfilePictureDTO;
import ma.pfa.api.dto.ResponseDTO;
import ma.pfa.api.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasAnyRole('ROLE_RECRUITER','ROLE_CANDIDATE')")
public class UserController {
    private final CustomUserDetailsService userService;

    @Autowired
    public UserController(CustomUserDetailsService userService) {
        this.userService = userService;
    }
    @PreAuthorize("hasAnyRole('ROLE_CANDIDATE')")
    @RequestMapping(value="/upload-profile-picture",method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<ResponseDTO> handleProfilePictureUpload(@ModelAttribute ProfilePictureDTO profilePictureDTO) throws HttpMediaTypeNotSupportedException {
        return userService.handleProfilePictureUpload(profilePictureDTO,null);
    }

}
