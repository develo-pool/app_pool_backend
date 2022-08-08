package appool.pool.project.brand_user;

import appool.pool.project.domain.BaseTimeEntity;
import appool.pool.project.user.PoolUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class BrandUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brandUser_id")
    private Long id;

    @Column(unique = true)
    private String brandUsername;

    private String brandInfo;

    @Column(name = "brandCategory")
    @ElementCollection(targetClass = String.class)
    @Builder.Default
    private List<String> brandCategory = new ArrayList<>();

    private Boolean brandAgreement;

    @Column(nullable = true, name = "brandProfileImage")
    private String brandProfileImage;

    @OneToOne(mappedBy = "brandUser")
    private PoolUser poolUser;


    public void confirmUser(PoolUser poolUser) {
        this.poolUser = poolUser;
        poolUser.addBrandUser(this);
    }

    public void addProfileImage(String brandProfileImage) {
        this.brandProfileImage = brandProfileImage;
    }

}
