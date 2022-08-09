package appool.pool.project.brand_user.dto;

import appool.pool.project.brand_user.BrandUser;
import appool.pool.project.follow.repository.FollowRepository;
import appool.pool.project.user.PoolUser;
import appool.pool.project.user.dto.UserInfoDto;
import appool.pool.project.user.repository.UserRepository;
import appool.pool.project.util.security.SecurityUtil;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BrandUserInfoDto {

    private String brandUsername;
    private String brandInfo;
    private String brandProfileImage;
    private UserInfoDto userInfoDto;
    private Long poolUserId;

    public BrandUserInfoDto(BrandUser brandUser) {
        UserInfoDto userInfoDto = UserInfoDto.builder()
                .build();

        this.brandUsername = brandUser.getBrandUsername();
        this.brandInfo = brandUser.getBrandInfo();
        this.brandProfileImage = brandUser.getBrandProfileImage();
        this.userInfoDto = userInfoDto;
        this.poolUserId = brandUser.getPoolUser().getId();
    }
}
