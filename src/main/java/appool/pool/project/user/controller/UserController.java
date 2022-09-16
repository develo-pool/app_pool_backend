package appool.pool.project.user.controller;

import appool.pool.project.brand_user.dto.BrandUserInfoDto;
import appool.pool.project.fcm.dto.TokenDTO;
import appool.pool.project.login.filter.JsonUsernamePasswordAuthenticationFilter;
import appool.pool.project.user.dto.*;
import appool.pool.project.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private static final int PAGE_DEFAULT_SIZE = 20;

    /**
     * 회원가입
     */
    @PostMapping("/signUp")
    @ResponseStatus(HttpStatus.OK)
    public void signUp(@Valid @RequestBody UserSignUpDto userSignUpDto, HttpServletRequest request, HttpServletResponse response) throws Exception{
        userService.signUp(userSignUpDto);
    }

    /**
     * 회원정보 수정
     */
    @PutMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public void updateBasicInfo(@Valid @RequestBody UserUpdateDto userUpdateDto) throws Exception {
        userService.update(userUpdateDto);
    }

    /**
     * 비밀번호 수정
     */
    @PutMapping("/user/password")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto) throws Exception{
        userService.updatePassword(updatePasswordDto.toBePassword(), updatePasswordDto.username());
    }

    /**
     * 회원탈퇴
     */
    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void withdraw() throws Exception {
        userService.withdraw();
    }

    /**
     * 회원정보 조회
     */
    @GetMapping("/user/{id}")
    public ResponseEntity getInfo(@Valid @PathVariable("id") Long id) throws Exception {
        UserInfoDto info = userService.getInfo(id);
        return new ResponseEntity(info, HttpStatus.OK);
    }

    /**
     * 내정보 조회
     */
    @GetMapping("/user")
    public ResponseEntity getMyInfo(HttpServletResponse response) throws Exception {
        UserInfoDto info = userService.getMyInfo();
        return new ResponseEntity(info, HttpStatus.OK);
    }

    /**
     * 중복검사
     */
    @GetMapping("/user-usernames/{username}/exists")
    public ResponseEntity<Boolean> checkUsernameDuplicate(@PathVariable String username) {
        return ResponseEntity.ok(userService.checkUsernameDuplicate(username));
    }

    @GetMapping("/user-nickNames/{nickName}/exists")
    public ResponseEntity<Boolean> checkNickNameDuplicate(@PathVariable String nickName) {
        return ResponseEntity.ok(userService.checkNickNameDuplicate(nickName));
    }

    @GetMapping("/user-phoneNumbers/{phoneNumber}/exists")
    public ResponseEntity<Boolean> checkPhoneNumberDuplicate(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(userService.checkPhoneNumberDuplicate(phoneNumber));
    }

    @PostMapping("/checkMember")
    public ResponseEntity<Boolean> checkMember(@RequestBody UserCheckDto userCheckDto) {
        return ResponseEntity.ok(userService.checkMemberInfo(userCheckDto));
    }

    /**
     * jwt 토큰 재발급 요청
     */
    @PostMapping("/reIssue")
    public TokenResponseDto reIssue(@RequestBody TokenRequestDto tokenRequestDto){
        TokenResponseDto tokenResponseDto = userService.reIssue(tokenRequestDto);
        return tokenResponseDto;
    }

    @GetMapping("/followings")
    public List<BrandUserInfoDto> getFollowingUserList(Long cursor) {
        return userService.getFollowingUsers(cursor, PageRequest.of(0, PAGE_DEFAULT_SIZE));
    }

    @PostMapping("/fcmToken")
    @ResponseStatus(HttpStatus.OK)
    public void saveFCMToken(@RequestBody TokenDTO fcmToken) {
        userService.saveFCMToken(fcmToken.getFcmToken());
    }


    @PutMapping("/user/update")
    public void updateUserNickName(@RequestBody String toBeNickname) {
        userService.updateUserNickName(toBeNickname);
    }

}
