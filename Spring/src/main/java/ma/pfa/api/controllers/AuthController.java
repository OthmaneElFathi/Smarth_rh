package ma.pfa.api.controllers;

import ma.pfa.api.dto.ResponseDTO;
import ma.pfa.api.dto.LoginDto;
import ma.pfa.api.dto.RegisterDto;
import ma.pfa.api.models.UserEntity;
import ma.pfa.api.service.IAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    public final IAuthService authService;

    @Autowired
    public AuthController(IAuthService authService) {
        this.authService=authService;
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginDto loginDto){
        return authService.login(loginDto);
    }
    @PostMapping(value = "/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterDto registerDto) {
            return authService.register(registerDto);
    }
    @GetMapping (value = "/user")
    public ResponseEntity<UserEntity> getCurrentUser() {
        return new ResponseEntity<>(authService.getCurrentUser(), HttpStatus.OK);
    }
}
