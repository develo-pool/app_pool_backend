package appool.pool.project.user.service;

import appool.pool.project.brand_user.BrandUser;
import appool.pool.project.brand_user.dto.BrandUserInfoDto;
import appool.pool.project.brand_user.exception.BrandUserException;
import appool.pool.project.brand_user.exception.BrandUserExceptionType;
import appool.pool.project.brand_user.repository.BrandUserRepository;
import appool.pool.project.follow.repository.FollowRepository;
import appool.pool.project.jwt.service.JwtService;
import appool.pool.project.user.dto.*;
import appool.pool.project.user.exception.PoolUserException;
import appool.pool.project.user.exception.PoolUserExceptionType;
import appool.pool.project.user.PoolUser;
import appool.pool.project.user.repository.UserRepository;
import appool.pool.project.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final FollowRepository followRepository;
    private final BrandUserRepository brandUserRepository;

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
    public void updatePassword(String toBePassword, String username) throws Exception {
        PoolUser poolUser = userRepository.findByUsername(username).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));
        poolUser.updatePassword(passwordEncoder, toBePassword);
    }

    @Override
    public void withdraw(String checkPassword) {
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
        PoolUser findPoolUser = userRepository.findByUsername((SecurityUtil.getLoginUsername())).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));

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
    public boolean checkPhoneNumberDuplicate(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean checkMemberInfo(UserCheckDto userCheckDto) {
        return (userRepository.existsByUsername(userCheckDto.getUsername()) && userRepository.existsByPhoneNumber(userCheckDto.getPhoneNumber()));
    }

    @Override
    public void newPassword(String newPassword) {
        PoolUser poolUser = userRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));
        poolUser.updatePassword(passwordEncoder, newPassword);
    }

    @Override
    public TokenResponseDto reIssue(TokenRequestDto requestDto) {
        if (!jwtService.isTokenValid(requestDto.getRefreshToken())) {
            throw new PoolUserException(PoolUserExceptionType.TOKEN_INVALID);
        }

        PoolUser poolUser = userRepository.findByRefreshToken(requestDto.getRefreshToken()).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));

        String accessToken = jwtService.createAccessToken(poolUser.getUsername(), poolUser.getNickName(), poolUser.getUserStatus().value());
        String refreshToken = jwtService.createRefreshToken();
        poolUser.updateRefreshToken(refreshToken);
        return new TokenResponseDto(accessToken, refreshToken);
    }

    @Override
    public List<BrandUserInfoDto> getFollowingUsers(Long cursor, Pageable pageable) {
        PoolUser loginUser = userRepository.findByUsername((SecurityUtil.getLoginUsername())).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));

        List<BrandUserInfoDto> followingUserList = getFollowingList(cursor, pageable).stream()
                .map(BrandUserInfoDto::new)
                .collect(Collectors.toList());
        followingUserList.forEach(f -> f.setUserInfoDto(UserInfoDto.builder()
                .userFollowerCount(followRepository.findFollowerCountById(f.getPoolUserId()))
                .follow(followRepository.findFollowByFromUserIdAndToUserId(loginUser.getId(), f.getPoolUserId())!= null)
                .build()));

        return followingUserList;
    }

    @Override
    public void saveFCMToken(String fcmToken) {
        PoolUser loginUser = userRepository.findByUsername((SecurityUtil.getLoginUsername()))
                .orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));
        loginUser.updateFCMToken(fcmToken);
    }

    @Override
    public void updateUserNickName(String toBeNickName) {
        PoolUser poolUser = userRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));
        poolUser.updateNickName(toBeNickName);
    }

    private List<BrandUser> getFollowingList(Long id, Pageable page) {
        PoolUser poolUser = userRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));
        return id.equals(0L)
                ? brandUserRepository.followingList(poolUser.getId(), page)
                : brandUserRepository.followingListLess(poolUser.getId(), id, page);
    }


}
