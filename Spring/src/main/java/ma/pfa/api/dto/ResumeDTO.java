package ma.pfa.api.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class ResumeDTO implements Serializable {
    private String title;
    private MultipartFile resume;
}
