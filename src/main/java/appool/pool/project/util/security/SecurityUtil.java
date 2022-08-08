package appool.pool.project.util.security;

import appool.pool.project.user.PoolUser;
import appool.pool.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@RequiredArgsConstructor
public class SecurityUtil {
    static UserRepository userRepository;
    public static String getLoginUsername() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return user.getUsername();
    }

    public static Long getLoginUserId() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();

        Optional<PoolUser> poolUser = userRepository.findByUsername(username);

        return poolUser.get().getId();
    }
}
