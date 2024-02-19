package ma.pfa.api.service;

import ma.pfa.api.dto.*;
import ma.pfa.api.enums.Roles;
import ma.pfa.api.models.Candidate;
import ma.pfa.api.models.Recruiter;
import ma.pfa.api.models.Role;
import ma.pfa.api.models.UserEntity;
import ma.pfa.api.repository.RoleRepository;
import ma.pfa.api.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService implements IAuthService{
    private final CustomUserDetailsService userDetailsService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;

    @Autowired
    public AuthService(CustomUserDetailsService userDetailsService, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTGenerator jwtGenerator) {
        this.userDetailsService = userDetailsService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginDto loginDto){
        try {
            System.out.println(loginDto);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()));
            System.out.println(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserEntity user = userDetailsService.getUserByUsername(loginDto.getUsername());

            String token = jwtGenerator.generateJwtToken(authentication);
            ResponseDTO responseDTO;
            if(user.getProfilePicture() != null)
            {
                responseDTO = ResponseDTO.builder().
                        jwtToken(token).
                        username(loginDto.getUsername()).
                        profilePicture(user.getProfilePicture().getImage()).
                        roles(authentication.getAuthorities().stream().
                                map(GrantedAuthority::getAuthority).
                                collect(Collectors.toList())).build();
            }
            else
            {
                responseDTO = ResponseDTO.builder().
                        jwtToken(token).
                        username(loginDto.getUsername()).
                        roles(authentication.getAuthorities().stream().
                                map(GrantedAuthority::getAuthority).
                                collect(Collectors.toList())).build();
            }

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        } catch (BadCredentialsException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(new ResponseDTO("User doesnt exist"), HttpStatus.UNAUTHORIZED);
        }

    }

    @Override
    public UserEntity getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userDetailsService.getUserByUsername(userDetails.getUsername());
        return user;
    }

    @Override
    public ResponseEntity<ResponseDTO> register(RegisterDto registerDto) {
        if (userDetailsService.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>(new ResponseDTO("Username taken"), HttpStatus.BAD_REQUEST);
        }
        UserEntity user;
        Role role = roleRepository.findByAuthority(registerDto.getRole());
        if (role.getAuthority().equals(Roles.ROLE_CANDIDATE.name())) {
            Candidate candidate = new Candidate();
            candidate.setDescription(registerDto.getDescription());
            user = candidate;
        } else if (role.getAuthority().equals(Roles.ROLE_RECRUITER.name())) {
            Recruiter recruiter = new Recruiter();
            recruiter.setCompanyName(registerDto.getCompanyName());
            recruiter.setContactNumber(registerDto.getContactNumber());
            recruiter.setCompanyAddress(registerDto.getCompanyAddress());
            user = recruiter;
        } else {
            return new ResponseEntity<>(new ResponseDTO("invalid role"), HttpStatus.BAD_REQUEST);
        }
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setFirstName(registerDto.getFirstName());
        user.setEmail(registerDto.getEmail());
        user.setLastName(registerDto.getLastName());
        user.setAuthorities(List.of(role));
        if(registerDto.getProfilePicture()!=null)
        {
            System.out.println("test");
            userDetailsService.handleProfilePictureUpload(new ProfilePictureDTO(registerDto.getProfilePicture()),user);
        }
        else
        {
            System.out.println("no");
            userDetailsService.save(user);
        }

        return new ResponseEntity<>(new ResponseDTO("success"), HttpStatus.OK);
    }
}

