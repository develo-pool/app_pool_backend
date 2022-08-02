package appool.pool.project.brand_user.dto;

import appool.pool.project.brand_user.BrandUser;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BrandUserInfoDto {

    private String brandUsername;
    private String brandInfo;
    private String brandProfileImage;

    @Builder
    public BrandUserInfoDto(BrandUser brandUser) {
        this.brandUsername = brandUser.getBrandUsername();
        this.brandInfo = brandUser.getBrandInfo();
        this.brandProfileImage = brandUser.getBrandProfileImage();
    }
}
