package appool.pool.project.message.service;

import appool.pool.project.user.UserStatus;
import appool.pool.project.user.dto.UserSignUpDto;
import appool.pool.project.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MessageServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    private static final String USERNAME = "username";
    private static final String PASSWORD = "PASSWORD!@";

    private void clear() {
        em.flush();
        em.clear();
    }

    private void deleteFile(String filePath) {
        File files = new File(filePath);
        files.delete();
    }

    private MockMultipartFile getMockUploadFile() throws IOException {
        return new MockMultipartFile("file", "file.png", "image/png", new FileInputStream("/Users/johnn/Desktop/img/diva.png"));
    }

    @BeforeEach
    private void signUpAndSetAuthentication() throws Exception {
        userService.signUp(new UserSignUpDto(USERNAME, PASSWORD, "testNickName", "01012345678", "MALE", "981029", true, true));
        SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
        emptyContext.setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        User.builder()
                                .username(USERNAME)
                                .password(PASSWORD)
                                .roles(UserStatus.USER.toString())
                                .build(),
                        null)
        );
    }

}