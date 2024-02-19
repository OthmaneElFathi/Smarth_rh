package ma.pfa.api.service;

import ma.pfa.api.dto.ProfilePictureDTO;
import ma.pfa.api.dto.ResponseDTO;
import ma.pfa.api.models.*;
import ma.pfa.api.repository.PermissionRepository;
import ma.pfa.api.repository.RoleRepository;
import ma.pfa.api.repository.UserRepository;
import ma.pfa.api.vo.RoleVo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    final private UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PermissionRepository permissionRepository;
    private final IProfilePictureService profilePictureService;

    @Autowired
    public CustomUserDetailsService(IProfilePictureService profilePictureService, UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.permissionRepository = permissionRepository;
        this.profilePictureService = profilePictureService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getAuthorities()));
    }


    public UserEntity getUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public void save(RoleVo roleVo) {
        Role role = modelMapper.map(roleVo, Role.class);
        role.setAuthorities(role.getAuthorities().stream().map(bo ->
                        permissionRepository.findByAuthority(bo.getAuthority())).
                collect(Collectors.toList()));
        roleRepository.save(role);
    }

    public void save(UserEntity user) {
        userRepository.save(user);
    }

    public ResponseEntity<ResponseDTO> handleProfilePictureUpload(@ModelAttribute ProfilePictureDTO profilePictureDTO, UserEntity user) {
        try {
            if (profilePictureDTO.getProfilePicture().getOriginalFilename() != null) {
                if (!isValidImageFileType(profilePictureDTO.getProfilePicture().getOriginalFilename())) {
                    return new ResponseEntity<>(new ResponseDTO("invalid image type"), HttpStatus.BAD_REQUEST);
                }
                if (user == null) {
                    storeProfilePicture(profilePictureDTO);
                } else {
                    storeProfilePictureRegister(profilePictureDTO, user);
                }
                return new ResponseEntity<>(new ResponseDTO("image uploaded"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseDTO("error in image upload"), HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDTO("error in image upload"), HttpStatus.BAD_REQUEST);
        }

    }

    private String storeProfilePictureRegister(ProfilePictureDTO profilePictureDTO, UserEntity user) throws IOException {
        ProfilePicture profilePicture = profilePictureService.addProfilePicture(user, profilePictureDTO.getProfilePicture());
        user.addProfilePicture(profilePicture);
        userRepository.save(user);
        return profilePicture.getId();
    }

    private boolean isValidImageFileType(String fileName) {
        return fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg") || fileName.toLowerCase().endsWith(".png");
    }

    public String storeProfilePicture(ProfilePictureDTO profilePictureDTO) throws IOException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUserUsername = userDetails.getUsername();
        UserEntity user = userRepository.findByUsername(currentUserUsername).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("Candidate not found with username: " + currentUserUsername);
        }
        ProfilePicture profilePicture = profilePictureService.addProfilePicture(user, profilePictureDTO.getProfilePicture());
        user.addProfilePicture(profilePicture);
        userRepository.save(user);
        return profilePicture.getId();
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
