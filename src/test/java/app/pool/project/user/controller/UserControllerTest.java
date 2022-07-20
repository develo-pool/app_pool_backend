package app.pool.project.user.controller;

import app.pool.project.user.PoolUser;
import app.pool.project.user.dto.UserSignUpDto;
import app.pool.project.user.repository.UserRepository;
import app.pool.project.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EntityManager em;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    PasswordEncoder passwordEncoder;

    private static String SIGN_UP_URL = "/signUp";
    private String username = "username";
    private String password = "password1234@";
    private String nickName = "doha";

    private void clear() {
        em.flush();
        em.clear();
    }

    private void signUp(String signUpData) throws Exception {
        mockMvc.perform(
                post(SIGN_UP_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signUpData))
                .andExpect(status().isOk());
    }

    @Value("${jwt.access.header}")
    private String accessHeader;

    private static final String BEARER = "Bearer ";

    private String getAccessToken() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);

        MvcResult result = mockMvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isOk()).andReturn();

        return result.getResponse().getHeader(accessHeader);
    }

    @Test
    public void test_signUp() throws Exception {
        //given
        String signUpData = objectMapper.writeValueAsString(new UserSignUpDto(username, password, nickName));

        //when
        signUp(signUpData);

        //then
        PoolUser poolUser = userRepository.findByUsername(username).orElseThrow(() -> new Exception("회원 없습니다."));
        assertThat(poolUser.getNickName()).isEqualTo(nickName);
        assertThat(userRepository.findAll().size()).isEqualTo(1);
    }

}