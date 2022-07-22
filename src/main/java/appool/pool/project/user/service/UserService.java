package appool.pool.project.user.service;


import appool.pool.project.user.dto.UserInfoDto;
import appool.pool.project.user.dto.UserSignUpDto;
import appool.pool.project.user.dto.UserUpdateDto;

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


}
