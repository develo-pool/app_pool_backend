package appool.pool.project.user.service;


import appool.pool.project.brand_user.dto.BrandUserInfoDto;
import appool.pool.project.user.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    void signUp(UserSignUpDto userSignUpDto) throws Exception;

    void update(UserUpdateDto userUpdateDto) throws Exception;

    void updatePassword(String toBePassword, String username) throws Exception;

    void withdraw(String checkPassword) ;

    UserInfoDto getInfo(Long id) throws Exception;

    UserInfoDto getMyInfo() throws Exception;

    boolean checkUsernameDuplicate(String username);

    boolean checkNickNameDuplicate(String nickName);

    boolean checkPhoneNumberDuplicate(String phoneNumber);

    boolean checkMemberInfo(UserCheckDto userCheckDto);

    void newPassword(String newPassword);

    TokenResponseDto reIssue(TokenRequestDto requestDto);

    List<BrandUserInfoDto> getFollowingUsers(Long cursor, Pageable pageable);

    void saveFCMToken(String fcmToken);

    void updateUserNickName(String toBeNickName);

}
