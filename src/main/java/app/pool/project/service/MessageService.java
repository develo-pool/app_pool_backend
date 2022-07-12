package app.pool.project.service;

import app.pool.project.entity.Message;
import app.pool.project.repository.MessageRepository;
import app.pool.project.request.MessageCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

//    @Value("${messageImg.path}")
    private String uploadFolder;


    public void write(MessageCreate messageCreate) {

        Message message = Message.builder()
                .title(messageCreate.getTitle())
                .body(messageCreate.getBody())
                .messageLink(messageCreate.getMessageLink())
                .build();
    }


}
