package appool.pool.project.user.dto;

import appool.pool.project.user.PoolUser;

import javax.validation.constraints.NotBlank;
import java.util.List;

public record UserSignUpDto(@NotBlank(message = "아이디는 필수 입력 사항입니다.") String username,
                            @NotBlank(message = "비밀번호는 필수 입력 사항입니다.") String password,
                            @NotBlank(message = "닉네임은 필수 입력 사항입니다.") String nickName,
                            @NotBlank(message = "전화번호는 필수 입력 사항입니다.") String phoneNumber,
                            String gender,
                            String birthday,
                            Boolean termAgreement,
                            Boolean privacyAgreement,
                            List<String> category) {

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
                .category(category)
                .build();
    }
}
