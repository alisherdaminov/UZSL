package UZSL.entity.clubs.clubsInfo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clubs_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubsProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String clubsProfileId;
    @Column(name = "stadium_name")
    private String stadiumName;
    @Column(name = "founded")
    private String founded;
    @Column(name = "clubs_color")
    private String clubsColor;
    @Column(name = "street")
    private String street;
    @Column(name = "city")
    private String city;
    @Column(name = "direction")
    private String direction;
    @Column(name = "phone")
    private String phone;
    @Column(name = "fax")
    private String fax;
    @Column(name = "website_name")
    private String websiteName;
    @Column(name = "email_name")
    private String emailName;

    @OneToOne(fetch = FetchType.LAZY)
    private ClubsSquadEntity clubsSquadProfile;

}
