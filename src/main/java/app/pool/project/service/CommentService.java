package app.pool.project.service;

import app.pool.project.domain.Comment;
import app.pool.project.domain.Message;
import app.pool.project.exception.MessageNotFound;
import app.pool.project.repository.CommentRepository;
import app.pool.project.repository.MessageRepository;
import app.pool.project.response.CommentResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MessageRepository messageRepository;

    @Transactional
    public void addComment(String body, long messageId) {
        Message message = messageRepository.findById(messageId).get();
        Comment comment = Comment.builder()
                .body(body)
                .message(message)
                .build();
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(long id) {
        Comment comment= commentRepository.findById(id)
                .orElseThrow(MessageNotFound::new);
        commentRepository.delete(comment);
    }

//    public CommentResponse get(Long id) {
//        commentRepository.(id)
//    }


}
