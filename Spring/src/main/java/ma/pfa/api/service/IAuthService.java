package ma.pfa.api.service;

import ma.pfa.api.dto.ResponseDTO;
import ma.pfa.api.dto.LoginDto;
import ma.pfa.api.dto.RegisterDto;
import ma.pfa.api.models.UserEntity;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    ResponseEntity<ResponseDTO> register (RegisterDto registerDto);
    ResponseEntity<ResponseDTO> login (LoginDto loginDto);

    UserEntity getCurrentUser();
}
