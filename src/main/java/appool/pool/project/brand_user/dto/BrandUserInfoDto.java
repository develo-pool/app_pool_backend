package appool.pool.project.brand_user.dto;

import appool.pool.project.brand_user.BrandUser;
import appool.pool.project.brand_user.repository.BrandUserRepository;
import appool.pool.project.follow.repository.FollowRepository;
import appool.pool.project.user.PoolUser;
import appool.pool.project.user.dto.UserInfoDto;
import appool.pool.project.user.repository.UserRepository;
import appool.pool.project.util.security.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    BrandUserRepository brandUserRepository;
    FollowRepository followRepository;
    UserRepository userRepository ;

    public BrandUserInfoDto(BrandUser brandUser) {
        Optional<PoolUser> loginUser = userRepository.findByUsername(SecurityUtil.getLoginUsername());
        UserInfoDto userInfoDto = UserInfoDto.builder()
                .follow(followRepository.findFollowByFromUserIdAndToUserId(loginUser.get().getId(), brandUser.getPoolUser().getId()) != null)
                .userFollowerCount(followRepository.findFollowerCountById(brandUser.getPoolUser().getId()))
                .build();

        this.brandUsername = brandUser.getBrandUsername();
        this.brandInfo = brandUser.getBrandInfo();
        this.brandProfileImage = brandUser.getBrandProfileImage();
        this.userInfoDto = userInfoDto;
    }
}
