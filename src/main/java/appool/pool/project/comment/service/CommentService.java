package appool.pool.project.comment.service;

import appool.pool.project.brand_user.exception.BrandUserException;
import appool.pool.project.brand_user.exception.BrandUserExceptionType;
import appool.pool.project.brand_user.repository.BrandUserRepository;
import appool.pool.project.comment.Comment;
import appool.pool.project.comment.dto.CommentCreate;
import appool.pool.project.comment.dto.CommentResponse;
import appool.pool.project.comment.repository.CommentRepository;
import appool.pool.project.exception.BaseExceptionType;
import appool.pool.project.message.exception.MessageException;
import appool.pool.project.message.exception.MessageExceptionType;
import appool.pool.project.message.repository.MessageRepository;
import appool.pool.project.user.PoolUser;
import appool.pool.project.user.exception.PoolUserException;
import appool.pool.project.user.exception.PoolUserExceptionType;
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
    private final BrandUserRepository brandUserRepository;

    public void create(CommentCreate commentCreate, Long messageId) {
        Comment comment = commentCreate.toEntity();
        comment.confirmWriter(userRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER)));
        comment.confirmMessage(messageRepository.findById(messageId).orElseThrow(() ->new MessageException(MessageExceptionType.MESSAGE_NOT_FOUND)));

        commentRepository.save(comment);
    }

    public List<CommentResponse> getList(Long messageId, Long cursor, Pageable pageable) {
        List<CommentResponse> commentList = getCommentList(messageId, cursor, pageable).stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());

        commentList.forEach(f -> {
            if(brandUserRepository.findByPoolUserId(f.getWriter().getPoolUserId()).isEmpty() == false) {
                f.getWriter().getBrandUserInfoDto().setBrandUsername(brandUserRepository.findByPoolUserId(f.getWriter().getPoolUserId()).orElseThrow(() -> new BrandUserException(BrandUserExceptionType.NOT_FOUND_BRAND)).getBrandUsername());
                f.getWriter().getBrandUserInfoDto().setBrandProfileImage(brandUserRepository.findByPoolUserId(f.getWriter().getPoolUserId()).orElseThrow(() -> new BrandUserException(BrandUserExceptionType.NOT_FOUND_BRAND)).getBrandProfileImage());
            }
        });

        return commentList;
    }

    private List<Comment> getCommentList(Long messageId, Long id, Pageable pageable) {
        return id.equals(0L)
                ? commentRepository.commentList(messageId, pageable)
                : commentRepository.commentListLess(messageId, id, pageable);
    }

    public CommentResponse getMyComment(Long messageId) {
        PoolUser loginUser = userRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));
        Comment myComment = commentRepository.findCommentByMessageIdAndWriterId(messageId, loginUser.getId());
        CommentResponse commentResponse = new CommentResponse(myComment);
        if(brandUserRepository.findByPoolUserId(myComment.getWriter().getId()).isEmpty() == false) {
            commentResponse.getWriter().getBrandUserInfoDto().setBrandUsername(brandUserRepository.findByPoolUserId(myComment.getWriter().getId()).orElseThrow(() -> new BrandUserException(BrandUserExceptionType.NOT_FOUND_BRAND)).getBrandUsername());
            commentResponse.getWriter().getBrandUserInfoDto().setBrandProfileImage(brandUserRepository.findByPoolUserId(myComment.getWriter().getId()).orElseThrow(() -> new BrandUserException(BrandUserExceptionType.NOT_FOUND_BRAND)).getBrandProfileImage());
        }
        return commentResponse;
    }

}
