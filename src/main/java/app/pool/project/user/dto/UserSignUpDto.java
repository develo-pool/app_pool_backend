package app.pool.project.user.dto;

import app.pool.project.user.PoolUser;

public record UserSignUpDto(String username, String password,
                            String nickname, String phoneNumber,
                            String gender, Integer birthday,
                            Boolean termAgreement, Boolean privacyAgreement) {

    public PoolUser toEntity() {
        return PoolUser.builder()
                .username(username)
                .password(password)
                .nickName(nickname)
                .phoneNumber(phoneNumber)
                .gender(gender)
                .birthday(birthday)
                .termAgreement(termAgreement)
                .privacyAgreement(privacyAgreement)
                .build();
    }
}
