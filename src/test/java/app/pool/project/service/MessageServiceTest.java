package app.pool.project.service;

import app.pool.project.domain.Message;
import app.pool.project.exception.MessageNotFound;
import app.pool.project.repository.MessageRepository;
import app.pool.project.request.MessageCreate;
import app.pool.project.request.MessageEdit;
import app.pool.project.request.MessageSearch;
import app.pool.project.response.MessageResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    void clean(){
        messageRepository.deleteAll();
    }

    @Test
    @DisplayName("메시지 작성")
    void test1() throws Exception {
        // given
        MessageCreate messageCreate = MessageCreate.builder()
                .title("제목입니다.")
                .body("본문입니다.")
                .messageLink("https://github.com/Lee-DoHa")
                .build();

        // when
        messageService.write(messageCreate, messageCreate.getFiles());

        // then
        Assertions.assertEquals(1L, messageRepository.count());

        Message message = messageRepository.findAll().get(0);
        Assertions.assertEquals("제목입니다.", message.getTitle());
        Assertions.assertEquals("본문입니다.", message.getBody());
        Assertions.assertEquals("https://github.com/Lee-DoHa", message.getMessageLink());

    }

    @Test
    @DisplayName("메시지 한개 조회")
    void test2() {
        // given
        Message requestMessage = Message.builder()
                .title("제목 예시입니다.")
                .body("본문 예시입니다.")
                .messageLink("https://github.com/Lee-DoHa")
                .build();
        messageRepository.save(requestMessage);


        // when
        MessageResponse response = messageService.get(requestMessage.getId());

        // then
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1L, messageRepository.count());
        Assertions.assertEquals("제목 예시입니다.", response.getTitle());
        Assertions.assertEquals("본문 예시입니다.", response.getBody());
        Assertions.assertEquals("https://github.com/Lee-DoHa", response.getMessageLink());
    }

    @Test
    @DisplayName("메시지 여러개 조회")
    void test3() {
        // given
        List<Message> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Message.builder()
                        .title("예시 제목 - " + i)
                        .body("예시 본문 - " + i)
                        .messageLink("예시 링크 - " + i)
                        .build())
                .collect(Collectors.toList());
        messageRepository.saveAll(requestPosts);

        MessageSearch messageSearch = MessageSearch.builder()
                .page(1)
                .build();


        // when
        List<MessageResponse> posts = messageService.getList(messageSearch);

        // then
        assertEquals(10L, posts.size());
        assertEquals("예시 제목 - 19", posts.get(0).getTitle());
    }

    @Test
    @DisplayName("글 제목 수정")
    void test4() {
        // given
        Message message = Message.builder()
                .title("제목 수정 전")
                .body("본문 수정 전")
                .messageLink("링크 수정 전")
                .build();
        messageRepository.save(message);

        MessageEdit messageEdit = MessageEdit.builder()
                .title("제목 수정 후")
                .build();

        // when
        messageService.edit(message.getId(), messageEdit);

        // then
        Message changedMessage = messageRepository.findById(message.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + message.getId()));
        Assertions.assertEquals("제목 수정 후", changedMessage.getTitle());

    }

    @Test
    @DisplayName("메시지 본문 수정")
    void test5() {
        // given
        Message message = Message.builder()
                .title("제목 수정 전")
                .body("본문 수정 전")
                .messageLink("링크 수정 전")
                .build();
        messageRepository.save(message);

        MessageEdit postEdit = MessageEdit.builder()
                .body("본문 수정 후")
                .build();

        // when
        messageService.edit(message.getId(), postEdit);

        // then
        Message changedPost = messageRepository.findById(message.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + message.getId()));
        Assertions.assertEquals("본문 수정 후", changedPost.getBody());

    }

    @Test
    @DisplayName("메시지 삭제")
    void test6() {
        // given
        Message message = Message.builder()
                .title("제목입니다.")
                .body("본문입니다.")
                .messageLink("링크입니다.")
                .build();
        messageRepository.save(message);

        // when
        messageService.delete(message.getId());

        // then
        Assertions.assertEquals(0, messageRepository.count());

    }

    @Test
    @DisplayName("메시지 한개 조회 - 존재하지 않는 글")
    void test7() {
        // given
        Message message = Message.builder()
                .title("제목")
                .body("본문")
                .messageLink("링크")
                .build();
        messageRepository.save(message);

        // expected
        assertThrows(MessageNotFound.class, () -> {
            messageService.get(message.getId() + 1L);
        });
    }

    @Test
    @DisplayName("메시지 삭제 - 존재하지 않는 글")
    void test8() {
        // given
        Message message = Message.builder()
                .title("제목")
                .body("본문")
                .messageLink("링크")
                .build();
        messageRepository.save(message);

        // expected
        assertThrows(MessageNotFound.class, () -> {
            messageService.delete(message.getId() + 1L);
        });
    }

    @Test
    @DisplayName("글 수정 - 존재하지 않는 글")
    void test9() {
        // given
        Message message = Message.builder()
                .title("제목")
                .body("본문")
                .messageLink("링크")
                .build();
        messageRepository.save(message);

        MessageEdit postEdit = MessageEdit.builder()
                .title("제목 수정 후")
                .body("본문 수정 후")
                .messageLink("링크 수정 후")
                .build();

        // expected
        assertThrows(MessageNotFound.class, () -> {
            messageService.edit(message.getId() + 1L, postEdit);
        });
    }

}