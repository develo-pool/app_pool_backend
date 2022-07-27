package appool.pool.project.message.service;

import appool.pool.project.message.Message;
import appool.pool.project.message.dto.MessageCreate;
import appool.pool.project.user.UserStatus;
import appool.pool.project.user.dto.UserSignUpDto;
import appool.pool.project.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    List<String> category = new ArrayList<String>();

    private void clear() {
        em.flush();
        em.clear();
    }

    private void deleteFile(String filePath) {
        File files = new File(filePath);
        files.delete();
    }

    private MockMultipartFile getMockUploadFile() throws IOException {
        return new MockMultipartFile("diva", "diva.png", "image/png", new FileInputStream("/Users/johnn/Desktop/img/diva.png"));
    }

    @BeforeEach
    private void signUpAndSetAuthentication() throws Exception {
        category.add("category1");
        category.add("category2");
        userService.signUp(new UserSignUpDto(USERNAME, PASSWORD, "testNickName", "01012345678", "MALE", "981029", true, true, category));
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
        SecurityContextHolder.setContext(emptyContext);
        clear();
    }


//    @Test
//    public void 메시지_저장_성공_업로드_파일_없음() throws Exception {
//        // given
//        String title = "제목 테스트";
//        String body = "본문 테스트";
//        String messageLink = "링크 테스트";
//        MessageCreate messageCreate = new MessageCreate(title, body, messageLink, Optional.empty());
//
//        // when
//        messageService.write(messageCreate);
//        clear();
//
//        // then
//        Message findMessage = em.createQuery("select p from Message p", Message.class).getSingleResult();
//        Message message = em.find(Message.class, findMessage.getId());
//        assertThat(message.getBody()).isEqualTo(body);
//        assertThat(message.getWriter().getUsername()).isEqualTo(USERNAME);
//        assertThat(message.getFilePath()).isNull();
//
//    }
//
//    @Test
//    public void 메시지_저장_성공_업로드_파일_있음() throws Exception {
//        // given
//        String title = "제목 테스트";
//        String body = "본문 테스트";
//        String messageLink = "링크 테스트";
//        MessageCreate messageCreate = new MessageCreate(title, body, messageLink, Optional.ofNullable(getMockUploadFile()));
//
//        // when
//        messageService.write(messageCreate);
//        clear();
//
//        // then
//        Message findMessage = em.createQuery("select p from Message p", Message.class).getSingleResult();
//        Message message = em.find(Message.class, findMessage.getId());
//        assertThat(message.getBody()).isEqualTo(body);
//        assertThat(message.getWriter().getUsername()).isEqualTo(USERNAME);
//        assertThat(message.getFilePath()).isNotNull();
//
//        deleteFile(message.getFilePath());
//
//    }

}