package app.pool.project.user.dto;

import app.pool.project.user.PoolUser;

public record UserSignUpDto(String username, String password, String nickname) {

    public PoolUser toEntity() {
        return PoolUser.builder()
                .username(username)
                .password(password)
                .nickName(nickname)
                .build();
    }
}
