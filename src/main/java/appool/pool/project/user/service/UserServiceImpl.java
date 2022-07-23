package appool.pool.project.user.service;

import appool.pool.project.exception.PoolUserException;
import appool.pool.project.exception.PoolUserExceptionType;
import appool.pool.project.user.PoolUser;
import appool.pool.project.user.dto.UserInfoDto;
import appool.pool.project.user.dto.UserSignUpDto;
import appool.pool.project.user.dto.UserUpdateDto;
import appool.pool.project.user.repository.UserRepository;
import appool.pool.project.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


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
        return new UserInfoDto(findPoolUser);
    }

    @Override
    public UserInfoDto getMyInfo() throws Exception {
        PoolUser findPoolUser = userRepository.findByUsername((SecurityUtil.getLoginUsername()).toString()).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));
        return new UserInfoDto(findPoolUser);
    }
}
