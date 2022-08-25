package appool.pool.project.login.handler;

import appool.pool.project.user.PoolUser;
import appool.pool.project.user.repository.UserRepository;
import appool.pool.project.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
@Transactional
public class LoginSuccessJWTProvideHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String username = extractUsername(authentication);
        PoolUser poolUser = userRepository.findByUsername(username).get();
        String accessToken = jwtService.createAccessToken(username, poolUser.getNickName(), poolUser.getUserStatus().value());
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken, username);

        poolUser.updateRefreshToken(refreshToken);
        log.info(poolUser.getRefreshToken());

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
