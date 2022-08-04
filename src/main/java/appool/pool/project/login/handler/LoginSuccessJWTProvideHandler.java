package appool.pool.project.login.handler;

import appool.pool.project.user.PoolUser;
import appool.pool.project.user.repository.UserRepository;
import appool.pool.project.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static appool.pool.project.user.QPoolUser.poolUser;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessJWTProvideHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String username = extractUsername(authentication);
        String accessToken = jwtService.createAccessToken(username, String.valueOf(poolUser.nickName));
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken, username);


        Optional<PoolUser> poolUser = userRepository.findByUsername(username);
        poolUser.get().updateRefreshToken(refreshToken);

        log.info("로그인에 성공합니다. username: {}", username);
        log.info("AccessToken을 발급합니다. AccessToken: {}", accessToken);
        log.info("RefreshToken을 발급합니다. RefreshToken: {}", refreshToken);

        HttpSession session = request.getSession();
        session.setAttribute("user", authentication.getPrincipal());
        session.setAttribute("accessToken", accessToken);
        session.setAttribute("refreshToken", refreshToken);

    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }






}
