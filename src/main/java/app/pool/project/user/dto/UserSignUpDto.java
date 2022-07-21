package app.pool.project.user.dto;

import app.pool.project.user.PoolUser;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record UserSignUpDto(String username,
                            String password,
                            String nickName,
                            String phoneNumber,
                            String gender, Integer birthday,
                            Boolean termAgreement, Boolean privacyAgreement) {

    public PoolUser toEntity() {
        return PoolUser.builder()
                .username(username)
                .password(password)
                .nickName(nickName)
                .phoneNumber(phoneNumber)
                .gender(gender)
                .birthday(birthday)
                .termAgreement(termAgreement)
                .privacyAgreement(privacyAgreement)
                .build();
    }
}
