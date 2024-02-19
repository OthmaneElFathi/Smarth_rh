package ma.pfa.api;

import ma.pfa.api.enums.Roles;

import ma.pfa.api.service.CustomUserDetailsService;
import ma.pfa.api.vo.RoleVo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private final CustomUserDetailsService userDetailsService;
    public DataLoader(CustomUserDetailsService userDetailsService) {
        this.userDetailsService=userDetailsService;
    }
    @Override
    public void run(String... args) {
        try {
            RoleVo roleCandidate = RoleVo.builder().
                    authority(Roles.ROLE_CANDIDATE.name()).
                    authorities(List.of()).build();
            userDetailsService.save(roleCandidate);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        try {
            RoleVo roleRecruiter = RoleVo.builder().
                    authority(Roles.ROLE_RECRUITER.name()).
                    authorities(List.of()).build();
            userDetailsService.save(roleRecruiter);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}