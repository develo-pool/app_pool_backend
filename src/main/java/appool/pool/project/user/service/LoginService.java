package appool.pool.project.user.service;

import appool.pool.project.user.PoolUser;
import appool.pool.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PoolUser poolUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("아이디가 없습니다."));
        return User.builder()
                .username(poolUser.getUsername())
                .password(poolUser.getPassword())
                .roles(poolUser.getUserStatus().name())
                .build();

    }

    private UserDetails createUserDetails(PoolUser poolUser) {
        String role = poolUser.getUserStatus().value();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);

        return new org.springframework.security.core.userdetails.User(
                String.valueOf(poolUser.getId()),
                poolUser.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
}
