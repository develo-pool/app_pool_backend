package appool.pool.project.user.service;


import appool.pool.project.brand_user.dto.BrandUserInfoDto;
import appool.pool.project.user.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    /**
     * 회원가입
     * 정보수정
     * 회원탈퇴
     * 정보조회
     * 문자인증
     */

    void signUp(UserSignUpDto userSignUpDto) throws Exception;

    void update(UserUpdateDto userUpdateDto) throws Exception;

    void updatePassword(String checkPassword, String toBePassword) throws Exception;

    void withdraw(String checkPassword) throws Exception;

    UserInfoDto getInfo(Long id) throws Exception;

    UserInfoDto getMyInfo() throws Exception;

    boolean checkUsernameDuplicate(String username);

    boolean checkNickNameDuplicate(String nickName);

    TokenResponseDto reIssue(TokenRequestDto requestDto);

    List<BrandUserInfoDto> getFollowingUsers(Long cursor, Pageable pageable);

}
