package ma.pfa.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO {
    private String username;
    private String jwtToken;
    private List<String> roles = new ArrayList<>();
    private byte[] profilePicture;
    private String response;
    private HttpStatus status;
    public ResponseDTO(String response){
        this.response=response;
    }
    public ResponseDTO(String response,HttpStatus status){
        this.response=response;
        this.status=status;
    }
}
