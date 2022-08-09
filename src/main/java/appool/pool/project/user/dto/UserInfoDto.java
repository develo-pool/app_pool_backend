package appool.pool.project.user.dto;

import appool.pool.project.user.PoolUser;
import appool.pool.project.user.UserStatus;
import lombok.*;

@Data
@NoArgsConstructor
@Builder
@Getter
@AllArgsConstructor
public class UserInfoDto {

    private String username;
    private String nickName;
    private UserStatus userStatus;
    private boolean follow;
    private int userFollowerCount;
    private int userFollowingCount;

}
