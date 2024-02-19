package ma.pfa.api.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.pfa.api.dto.RecruiterDTO;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recruiters") public class Recruiter extends UserEntity {
    private String companyName;
    private String contactNumber;
    private String companyAddress;
    @OneToMany(mappedBy = "recruiter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobOffer> jobOffers = new ArrayList<>();
    public RecruiterDTO toDTO() {
        return RecruiterDTO.builder().email(this.getEmail()).companyName(this.getCompanyName()).firstName(this.getFirstName()).lastName(this.getLastName()).companyAddress(this.getCompanyAddress()).contactNumber(this.getContactNumber()).build();
    }
}
