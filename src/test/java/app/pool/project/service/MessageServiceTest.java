package app.pool.project.service;

import app.pool.project.entity.Message;
import app.pool.project.repository.MessageRepository;
import app.pool.project.request.MessageCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

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
    void test1() {
        // given
        MessageCreate messageCreate = MessageCreate.builder()
                .title("제목입니다.")
                .body("본문입니다.")
                .messageLink("https://github.com/Lee-DoHa")
                .build();

        // when
        messageService.write(messageCreate);

        // then
        Assertions.assertEquals(1L, messageRepository.count());

        Message message = messageRepository.findAll().get(0);
        Assertions.assertEquals("제목입니다.", message.getTitle());
        Assertions.assertEquals("본문입니다.", message.getBody());
        Assertions.assertEquals("https://github.com/Lee-DoHa", message.getMessageLink());

    }

}