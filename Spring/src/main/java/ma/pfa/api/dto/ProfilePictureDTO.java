package ma.pfa.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class ProfilePictureDTO implements Serializable {
    private MultipartFile profilePicture;
}
