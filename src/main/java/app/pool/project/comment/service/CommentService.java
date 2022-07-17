package app.pool.project.comment.service;

import app.pool.project.comment.Comment;
import app.pool.project.comment.dto.CommentResponse;
import app.pool.project.message.Message;
import app.pool.project.exception.MessageNotFound;
import app.pool.project.comment.repository.CommentRepository;
import app.pool.project.message.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<CommentResponse> getList(CommentSearch commentSearch) {
        return commentRepository.getList(commentSearch).stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
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
