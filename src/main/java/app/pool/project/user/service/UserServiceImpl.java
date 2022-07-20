package app.pool.project.user.service;

import app.pool.project.user.PoolUser;
import app.pool.project.user.dto.UserInfoDto;
import app.pool.project.user.dto.UserSignUpDto;
import app.pool.project.user.dto.UserUpdateDto;
import app.pool.project.user.repository.UserRepository;
import app.pool.project.util.security.SecurityUtil;
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
            throw new Exception("이미 존재하는 아이디입니다.");
        }

        userRepository.save(poolUser);
    }

    @Override
    public void update(UserUpdateDto userUpdateDto) throws Exception {
        PoolUser poolUser = userRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new Exception("회원이 존재하지 않습니다."));
        userUpdateDto.nickName().ifPresent(poolUser::updateNickName);
    }

    @Override
    public void updatePassword(String checkPassword, String toBePassword) throws Exception {
        PoolUser poolUser = userRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new Exception("회원이 존재하지 않습니다."));

        if(!poolUser.matchPassword(passwordEncoder, checkPassword)) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        poolUser.updatePassword(passwordEncoder, toBePassword);
    }

    @Override
    public void withdraw(String checkPassword) throws Exception {
        PoolUser poolUser = userRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new Exception("회원이 존재하지 않습니다."));

        if(!poolUser.matchPassword(passwordEncoder, checkPassword)) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(poolUser);
    }

    @Override
    public UserInfoDto getInfo(Long id) throws Exception {
        PoolUser findPoolUser = userRepository.findById(id).orElseThrow(() -> new Exception("회원이 없습니다."));
        return new UserInfoDto(findPoolUser);
    }

    @Override
    public UserInfoDto getMyInfo() throws Exception {
        PoolUser findPoolUser = userRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new Exception("회원이 없습니다."));
        return new UserInfoDto(findPoolUser);
    }
}
