package appool.pool.project.brand_user.dto;

import appool.pool.project.brand_user.BrandUser;
import appool.pool.project.user.dto.UserInfoDto;
import lombok.*;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BrandUserInfoDto {

    private Long brandUserId;
    private String brandUsername;
    private String brandInfo;
    private String brandProfileImage;
    private UserInfoDto userInfoDto;
    private Long poolUserId;
    private Boolean isLoginUser;

    public BrandUserInfoDto(BrandUser brandUser) {
        UserInfoDto userInfoDto = UserInfoDto.builder()
                .build();

        this.brandUserId = brandUser.getId();
        this.brandUsername = brandUser.getBrandUsername();
        this.brandInfo = brandUser.getBrandInfo();
        this.brandProfileImage = brandUser.getBrandProfileImage();
        this.userInfoDto = userInfoDto;
        this.poolUserId = brandUser.getPoolUser().getId();
        this.isLoginUser = false;
    }

}
