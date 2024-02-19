package ma.pfa.api.repository;

import ma.pfa.api.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PermissionRepository extends JpaRepository<Permission, String> {
    Permission findByAuthority(String name);
}
