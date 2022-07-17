package app.pool.project.user.service;

import app.pool.project.user.PoolUser;
import app.pool.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PoolUser poolUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("아이디가 없습니다."));
        return User.builder()
                .username(poolUser.getPhoneNumber())
                .password(poolUser.getPassword())
                .roles(poolUser.getUserStatus().name())
                .build();

    }
}
