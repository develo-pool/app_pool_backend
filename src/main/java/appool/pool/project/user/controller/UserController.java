package appool.pool.project.user.controller;

import appool.pool.project.user.dto.*;
import appool.pool.project.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping("/signUp")
    @ResponseStatus(HttpStatus.OK)
    public void signUp(@Valid @RequestBody UserSignUpDto userSignUpDto) throws Exception{
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
        userService.updatePassword(updatePasswordDto.checkPassword(), updatePasswordDto.toBePassword());
    }

    /**
     * 회원탈퇴
     */
    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void withdraw(@Valid @RequestBody UserWithdrawDto userWithdrawDto) throws Exception {
        userService.withdraw(userWithdrawDto.checkPassword());
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

}
