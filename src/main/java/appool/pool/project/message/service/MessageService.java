package appool.pool.project.message.service;

import appool.pool.project.message.Message;
import appool.pool.project.message.repository.MessageRepository;
import appool.pool.project.message.dto.MessageCreate;
import appool.pool.project.message.dto.MessageSearch;
import appool.pool.project.message.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public void write(MessageCreate messageCreate){

        Message message = Message.builder()
                .title(messageCreate.getTitle())
                .body(messageCreate.getBody())
                .messageLink(messageCreate.getMessageLink())
                .build();

        messageRepository.save(message);
    }


//    public MessageResponse get(Long id) {
//        Message message = messageRepository.findById(id);
//
//        return MessageResponse.builder()
//                .id(message.getId())
//                .title(message.getTitle())
//                .body(message.getBody())
//                .messageLink(message.getMessageLink())
//                .build();
//    }

    public List<MessageResponse> getList(MessageSearch messageSearch) {
        return messageRepository.getList(messageSearch).stream()
                .map(MessageResponse::new)
                .collect(Collectors.toList());
    }

//    @Transactional
//    public void edit(Long id, MessageEdit messageEdit) {
//        Message message = messageRepository.findById(id)
//                .orElseThrow(MessageNotFound::new);
//
//        MessageEditor.MessageEditorBuilder editorBuilder = message.toEditor();
//
//        if (messageEdit.getTitle() != null) {
//            editorBuilder.title(messageEdit.getTitle());
//        }
//
//        if (messageEdit.getBody() != null) {
//            editorBuilder.body(messageEdit.getBody());
//        }
//
//        if (messageEdit.getMessageLink() != null) {
//            editorBuilder.messageLink(messageEdit.getMessageLink());
//        }
//
//        message.edit(editorBuilder.build());
//    }
//
//    public void delete(Long id) {
//        Message message = messageRepository.findById(id)
//                .orElseThrow(MessageNotFound::new);
//
//        messageRepository.delete(message);
//    }

}
