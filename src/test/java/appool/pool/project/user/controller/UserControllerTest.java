package appool.pool.project.user.controller;

import appool.pool.project.user.exception.PoolUserExceptionType;
import appool.pool.project.user.PoolUser;
import appool.pool.project.user.dto.UserSignUpDto;
import appool.pool.project.user.repository.UserRepository;
import appool.pool.project.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    private String nickName = "doha12345";
    private String phoneNumber = "01053197659";
    private String gender = "MALE";
    private String birthday = "981029";
    private Boolean termAgreement = Boolean.TRUE;
    private Boolean privacyAgreement = Boolean.TRUE;
    private List<String> category = new ArrayList<>();


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

    private void signUpFail(String signUpData) throws Exception {
        mockMvc.perform(
                        post(SIGN_UP_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(signUpData))
                .andExpect(status().isBadRequest());
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
        category.add("category1");
        category.add("category2");
        String signUpData = objectMapper.writeValueAsString(new UserSignUpDto(username, password, "doha12345", phoneNumber, gender, birthday, termAgreement, privacyAgreement, category));

        //when
        signUp(signUpData);

        //then
        PoolUser poolUser = userRepository.findByUsername(username).orElseThrow(() -> new Exception("회원이 없습니다."));
        assertThat(poolUser.getNickName()).isEqualTo(nickName);
        assertThat(userRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    public void 회원가입_실패_필드가_없음() throws Exception {
        // given
        category.add("category1");
        category.add("category2");
        String noUsernameSignUpData = objectMapper.writeValueAsString(new UserSignUpDto(null, password, nickName, phoneNumber, gender, birthday, termAgreement, privacyAgreement, category));
        String noPasswordSignUpData = objectMapper.writeValueAsString(new UserSignUpDto(username, null, nickName, phoneNumber, gender, birthday, termAgreement, privacyAgreement, category));
        String noNickNameSignUpData = objectMapper.writeValueAsString(new UserSignUpDto(username, password, null, phoneNumber, gender, birthday, termAgreement, privacyAgreement, category));
        String noPhoneNumberSignUpData = objectMapper.writeValueAsString(new UserSignUpDto(username, password, nickName, null, gender, birthday, termAgreement, privacyAgreement, category));
        String noGenderSignUpData = objectMapper.writeValueAsString(new UserSignUpDto(username, password, nickName, phoneNumber, null, birthday, termAgreement, privacyAgreement, category));

        // expected
        signUpFail(noUsernameSignUpData); // 상태코드 400
        signUpFail(noPasswordSignUpData);
        signUpFail(noNickNameSignUpData);
        signUpFail(noPhoneNumberSignUpData);
        signUpFail(noGenderSignUpData);

        assertThat(userRepository.findAll().size()).isEqualTo(2);

    }

    @Test
    public void 내정보조회_성공() throws Exception {
        //given
        category.add("category1");
        category.add("category2");
        String signUpData = objectMapper.writeValueAsString(new UserSignUpDto(username, password, "doha123", phoneNumber, gender, birthday, termAgreement, privacyAgreement, category));
        signUp(signUpData);

        String accessToken = getAccessToken();


        //when
        MvcResult result = mockMvc.perform(
                        get("/user")
                                .characterEncoding(StandardCharsets.UTF_8)
                                .header(accessHeader, BEARER + accessToken))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();


        //then
        Map<String, Object> map = objectMapper.readValue(result.getResponse().getContentAsString(), Map.class);
        PoolUser poolUser = userRepository.findByUsername(username).orElseThrow(() -> new Exception("회원이 없습니다"));
        assertThat(poolUser.getUsername()).isEqualTo(map.get("username"));
        assertThat(poolUser.getNickName()).isEqualTo(map.get("nickName"));


    }

    @Test
    public void 회원정보조회_실패_없는회원조회() throws Exception {
        //given
        category.add("category1");
        category.add("category2");
        String signUpData = objectMapper.writeValueAsString(new UserSignUpDto(username, password, "dohaha1", phoneNumber, gender, birthday, termAgreement, privacyAgreement ,category));
        signUp(signUpData);

        String accessToken = getAccessToken();


        //when
        MvcResult result = mockMvc.perform(
                        get("/user/2211")
                                .characterEncoding(StandardCharsets.UTF_8)
                                .header(accessHeader, BEARER + accessToken))
                .andExpect(status().isNotFound()).andReturn();

        //then
        Map<String, Integer> map = objectMapper.readValue(result.getResponse().getContentAsString(), Map.class);
        assertThat(map.get("errorCode")).isEqualTo(PoolUserExceptionType.NOT_FOUND_MEMBER.getErrorCode());//빈 문자열
    }

}