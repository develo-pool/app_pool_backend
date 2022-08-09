package appool.pool.project.user.service;

import appool.pool.project.follow.repository.FollowRepository;
import appool.pool.project.follow.service.FollowService;
import appool.pool.project.jwt.service.JwtService;
import appool.pool.project.login.filter.JsonUsernamePasswordAuthenticationFilter;
import appool.pool.project.user.dto.*;
import appool.pool.project.user.exception.PoolUserException;
import appool.pool.project.user.exception.PoolUserExceptionType;
import appool.pool.project.user.PoolUser;
import appool.pool.project.user.repository.UserRepository;
import appool.pool.project.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final FollowRepository followRepository;



    @Override
    public void signUp(UserSignUpDto userSignUpDto) throws Exception {
        PoolUser poolUser = userSignUpDto.toEntity();
        poolUser.addUserAuthority();
        poolUser.encodePassword(passwordEncoder);

        if(userRepository.findByUsername(userSignUpDto.username()).isPresent()) {
            throw new PoolUserException(PoolUserExceptionType.ALREADY_EXIST_USERNAME);
        }

        userRepository.save(poolUser);

    }


    @Override
    public void update(UserUpdateDto userUpdateDto) throws Exception {
        PoolUser poolUser = userRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));
        userUpdateDto.nickName().ifPresent(poolUser::updateNickName);
    }

    @Override
    public void updatePassword(String checkPassword, String toBePassword) throws Exception {
        PoolUser poolUser = userRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));

        if(!poolUser.matchPassword(passwordEncoder, checkPassword)) {
            throw new PoolUserException(PoolUserExceptionType.WRONG_PASSWORD);
        }

        poolUser.updatePassword(passwordEncoder, toBePassword);
    }

    @Override
    public void withdraw(String checkPassword) throws Exception {
        PoolUser poolUser = userRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));

        if(!poolUser.matchPassword(passwordEncoder, checkPassword)) {
            throw new PoolUserException(PoolUserExceptionType.WRONG_PASSWORD);
        }

        userRepository.delete(poolUser);
    }

    @Override
    public UserInfoDto getInfo(Long id) throws Exception {
        PoolUser findPoolUser = userRepository.findById(id).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));
        UserInfoDto userInfoDto = UserInfoDto.builder()
                .username(findPoolUser.getUsername())
                .nickName(findPoolUser.getNickName())
                .userStatus(findPoolUser.getUserStatus())
                .build();

        return userInfoDto;
    }

    @Override
    public UserInfoDto getMyInfo() throws Exception {
        PoolUser findPoolUser = userRepository.findByUsername((SecurityUtil.getLoginUsername()).toString()).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));

        UserInfoDto userInfoDto = UserInfoDto.builder()
                .username(findPoolUser.getUsername())
                .nickName(findPoolUser.getNickName())
                .userStatus(findPoolUser.getUserStatus())
                .userFollowingCount(followRepository.findFollowingCountById(findPoolUser.getId()))
                .build();
        return userInfoDto;
    }

    @Override
    public boolean checkUsernameDuplicate(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean checkNickNameDuplicate(String nickName) {
        return userRepository.existsByNickName(nickName);
    }

    @Override
    public TokenResponseDto reIssue(TokenRequestDto requestDto) {
        if (!jwtService.isTokenValid(requestDto.getRefreshToken())) {
            throw new PoolUserException(PoolUserExceptionType.TOKEN_INVALID);
        }

        PoolUser poolUser = userRepository.findByRefreshToken(requestDto.getRefreshToken()).get();


        String accessToken = jwtService.createAccessToken(poolUser.getUsername(), poolUser.getNickName(), poolUser.getUserStatus().value());
        String refreshToken = jwtService.createRefreshToken();
        poolUser.updateRefreshToken(refreshToken);
        return new TokenResponseDto(accessToken, refreshToken);
    }





}
