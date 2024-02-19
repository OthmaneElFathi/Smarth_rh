package ma.pfa.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@Table(name = "profile_pictures")
@AllArgsConstructor
@NoArgsConstructor
public class ProfilePicture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private byte[] image;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
    public ProfilePicture(byte[] image)
    {
        this.image=image;
    }
}
