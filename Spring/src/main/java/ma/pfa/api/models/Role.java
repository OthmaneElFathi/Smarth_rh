package ma.pfa.api.models;

import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles") public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; 
    @Column(unique = true)
    private String authority;

    @ManyToMany()
    private List<Permission> authorities = new ArrayList<>();
}
