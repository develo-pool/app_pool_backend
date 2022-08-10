package appool.pool.project.comment.service;

import appool.pool.project.comment.Comment;
import appool.pool.project.comment.dto.CommentCreate;
import appool.pool.project.comment.dto.CommentResponse;
import appool.pool.project.message.Message;
import appool.pool.project.comment.repository.CommentRepository;
import appool.pool.project.message.repository.MessageRepository;
import appool.pool.project.user.PoolUser;
import appool.pool.project.user.repository.UserRepository;
import appool.pool.project.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final CommentRepository commentRepository;

    public void create(CommentCreate commentCreate, Long messageId) {
        Comment comment = commentCreate.toEntity();
        comment.confirmWriter(userRepository.findByUsername(SecurityUtil.getLoginUsername()).get());
        comment.confirmMessage(messageRepository.findById(messageId).get());

        commentRepository.save(comment);
    }

    public List<CommentResponse> getList(Long messageId, Long cursor, Pageable pageable) {
        return getCommentList(messageId, cursor, pageable).stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    private List<Comment> getCommentList(Long messageId, Long id, Pageable pageable) {
        return id.equals(0L)
                ? commentRepository.commentList(messageId, pageable)
                : commentRepository.commentListLess(messageId, id, pageable);
    }

    public CommentResponse getMyComment(Long messageId) {
        Optional<PoolUser> loginUser = userRepository.findByUsername(SecurityUtil.getLoginUsername());
        Comment myComment = commentRepository.findCommentByMessageIdAndWriterId(messageId, loginUser.get().getId());
        CommentResponse commentResponse = new CommentResponse(myComment);
        return commentResponse;
    }

}
