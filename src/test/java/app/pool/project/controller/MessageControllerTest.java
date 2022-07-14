package app.pool.project.controller;

import app.pool.project.domain.Message;
import app.pool.project.repository.MessageRepository;
import app.pool.project.request.MessageCreate;
import app.pool.project.request.MessageEdit;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class MessageControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    void clean() {
        messageRepository.deleteAll();
    }

    @Test
    @DisplayName("/messages 요청시 DB에 값이 저장된다.")
    void test() throws Exception{
        // given

        MessageCreate request = MessageCreate.builder()
                .title("제목입니다.")
                .body("내용입니다.")
                .messageLink("링크입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);// String -> JSON 으로 바꿔줌


        // expected
        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());

        // db -> message 하나 등록
    }

//    @Test
//    @DisplayName("/messages 요청시 title 값은 필수다.")
//    void test2() throws Exception{
//        // given
//
//        MessageCreate request = MessageCreate.builder()
//                .body("내용입니다.")
//                .build();
//
//        String json = objectMapper.writeValueAsString(request);// String -> JSON 으로 바꿔줌
//
//
//        // expected
//        mockMvc.perform(message("/messages")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json)
//                )
//                .andExpect(status().isBadRequest())
//                .andDo(print());
//    }

    @Test
    @DisplayName("/messages 요청시 DB에 값이 저장된다.")
    void test3() throws Exception{
        // given

        MessageCreate request = MessageCreate.builder()
                .title("제목입니다.")
                .body("내용입니다.")
                .messageLink("링크입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);// String -> JSON 으로 바꿔줌

        // when (이런 요청을 했을 때)
        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        // db -> message 1개 등록

        // then (이런 결과가 나온다.) 그래서 2개 나옴
        Assertions.assertEquals(1L,messageRepository.count());

        Message message = messageRepository.findAll().get(0);
        assertEquals("제목입니다.", message.getTitle());
        assertEquals("내용입니다.", message.getBody());
        assertEquals("링크입니다.", message.getMessageLink());
    }

    @Test
    @DisplayName("메시지 1개 조회")
    void test4() throws Exception {
        // given
        Message message = Message.builder()
                .title("제목")
                .body("본문")
                .messageLink("링크")
                .build();
        messageRepository.save(message);

        // expected(when과 then이 섞인거)
        mockMvc.perform(get("/messages/{messageId}", message.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(message.getId()))
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.body").value("본문"))
                .andExpect(jsonPath("$.messageLink").value("링크"))
                .andDo(print());
    }

    @Test
    @DisplayName("페이지를 1로 요청하면 첫 페이지를 가져온다.")
    void test5() throws Exception {
        // given
        List<Message> requestPosts = IntStream.range(1, 31)
                .mapToObj(i ->
                        Message.builder()
                                .title("제목 - " + i)
                                .body("본문 - " + i)
                                .messageLink("링크 - " + i)
                                .build())
                .collect(Collectors.toList());
        messageRepository.saveAll(requestPosts);

        // expected(when과 then이 섞인거)
        mockMvc.perform(get("/messages?page=1&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(10)))
                .andExpect(jsonPath("$[0].id").value(33))
                .andExpect(jsonPath("$[0].title").value("제목 - 30"))
                .andExpect(jsonPath("$[0].body").value("본문 - 30"))
                .andExpect(jsonPath("$[0].messageLink").value("링크 - 30"))
                .andDo(print());
    }

    @Test
    @DisplayName("메시지 제목 수정")
    void test6() throws Exception {
        // given
        Message message = Message.builder()
                .title("제목 수정 전")
                .body("본문 수정 전")
                .messageLink("링크 수정 전")
                .build();
        messageRepository.save(message);

        MessageEdit postEdit = MessageEdit.builder()
                .title("제목 수정 후")
                .build();

        // expected(when과 then이 섞인거)
        mockMvc.perform(MockMvcRequestBuilders.patch("/messages/{postId}", message.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("메시지 삭제")
    void test7() throws Exception {
        // given
        Message message = Message.builder()
                .title("제목")
                .body("본문")
                .messageLink("링크")
                .build();
        messageRepository.save(message);

        // expected(when과 then이 섞인거)
        mockMvc.perform(delete("/messages/{postId}", message.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    @DisplayName("존재하지 않는 메시지 조회")
    void test8() throws Exception {

        // expected
        mockMvc.perform(get("/messages/{postId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 메시지 수정")
    void test9() throws Exception {
        // given
        Message postEdit = Message.builder()
                .title("제목 수정 후")
                .body("본문 수정 후")
                .build();

        // expected
        mockMvc.perform(MockMvcRequestBuilders.patch("/messages/{postId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

//    @Test
//    @DisplayName("/게시글 작성시 제목에 '바보'는 포함될 수 없다.")
//    void test10() throws Exception{
//        // given
//        MessageCreate request = MessageCreate.builder()
//                .title("나는 바보입니다..")
//                .body("뿡뿡뿡내용.")
//                .build();
//
//        String json = objectMapper.writeValueAsString(request);// String -> JSON 으로 바꿔줌
//
//        // when (이런 요청을 했을 때)
//        mockMvc.perform(post("/messages")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json)
//                )
//                .andExpect(status().isBadRequest())
//                .andDo(print());
//    }

    @Test
    @DisplayName("/messages 여러개 조회")
    void test10() throws Exception{
        // given
        Message message1 = messageRepository.save(Message.builder()
                .title("제목1")
                .body("본문1")
                .messageLink("링크1")
                .build());

        Message message2 = messageRepository.save(Message.builder()
                .title("제목2")
                .body("본문2")
                .messageLink("링크2")
                .build());


        // expected
        mockMvc.perform(get("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id").value(message1.getId()))
                .andExpect(jsonPath("$[0].title").value("제목1"))
                .andExpect(jsonPath("$[0].body").value("본문1"))
                .andExpect(jsonPath("$[0].messageLink").value("링크1"))
                .andExpect(jsonPath("$[1].id").value(message2.getId()))
                .andExpect(jsonPath("$[1].title").value("제목2"))
                .andExpect(jsonPath("$[1].body").value("본문2"))
                .andExpect(jsonPath("$[1].messageLink").value("링크2"))
                .andDo(print());


    }

}