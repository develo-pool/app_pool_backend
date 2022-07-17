package app.pool.project.user.dto;

import app.pool.project.user.PoolUser;
import lombok.Builder;
import lombok.Data;

@Data
public class UserInfoDto {

    private final String nickName;

    @Builder
    public UserInfoDto(PoolUser poolUser) {
        this.nickName = poolUser.getNickName();
    }
}
