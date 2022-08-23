package appool.pool.project.user.dto;

import appool.pool.project.brand_user.dto.BrandUserInfoDto;
import appool.pool.project.user.UserStatus;
import lombok.*;

@Data
@NoArgsConstructor
@Builder
@Getter
@AllArgsConstructor
public class UserInfoDto {

    private Long poolUserId;
    private String username;
    private String nickName;
    private UserStatus userStatus;
    private boolean follow;
    private int userFollowerCount;
    private int userFollowingCount;
    private BrandUserInfoDto brandUserInfoDto;

}
