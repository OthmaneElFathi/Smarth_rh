package ma.pfa.api.models;

import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;     @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    private String firstName;
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)     private ProfilePicture profilePicture;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> authorities = new ArrayList<>();

    public ProfilePicture getProfilePicture() {
        if(profilePicture!=null)
            return profilePicture;
        return new ProfilePicture(new byte[0]);
    }

    public void addProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
        profilePicture.setUser(this);     }
    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, firstName, lastName);
    }
}
