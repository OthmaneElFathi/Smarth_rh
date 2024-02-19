package ma.pfa.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenVo implements Serializable {
    private String username;
    private String jwtToken;
    private List<String> roles = new ArrayList<>();
}
