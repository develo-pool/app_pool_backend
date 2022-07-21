package app.pool.project.user.dto;

import app.pool.project.user.PoolUser;
import app.pool.project.user.UserStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoDto {

    private String username;
    private String nickName;
    private UserStatus userStatus;

    @Builder
    public UserInfoDto(PoolUser poolUser) {
        this.username = poolUser.getUsername();
        this.nickName = poolUser.getNickName();
        this.userStatus = poolUser.getUserStatus();
    }
}
