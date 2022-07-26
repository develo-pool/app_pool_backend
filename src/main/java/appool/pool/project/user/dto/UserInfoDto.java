package appool.pool.project.user.dto;

import appool.pool.project.user.PoolUser;
import appool.pool.project.user.UserStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoDto {

    private String username;
    private String nickName;
    private UserStatus userStatus;
    private Boolean termAgreement;
    private Boolean privacyAgreement;

    @Builder
    public UserInfoDto(PoolUser poolUser) {
        this.username = poolUser.getUsername();
        this.nickName = poolUser.getNickName();
        this.userStatus = poolUser.getUserStatus();
        this.termAgreement = poolUser.getTermAgreement();
        this.privacyAgreement = poolUser.getPrivacyAgreement();
    }
}
