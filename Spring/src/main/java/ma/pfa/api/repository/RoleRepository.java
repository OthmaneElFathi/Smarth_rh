package ma.pfa.api.repository;

import ma.pfa.api.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByAuthority(String name);
}
