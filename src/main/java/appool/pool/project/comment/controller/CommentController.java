package appool.pool.project.comment.controller;

import appool.pool.project.comment.dto.CommentCreate;
import appool.pool.project.comment.dto.CommentResponse;
import appool.pool.project.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private static final int PAGE_DEFAULT_SIZE = 3;

    @PostMapping("/{messageId}")
    public void comment(@RequestBody @Valid CommentCreate commentCreate, @PathVariable Long messageId) {
        commentService.create(commentCreate, messageId);
    }

    @GetMapping("/{messageId}/my")
    public ResponseEntity getMyComment(@PathVariable Long messageId) {
        return ResponseEntity.ok(commentService.getMyComment(messageId));
    }

    @GetMapping("/{messageId}")
    public List<CommentResponse> getCommentList(@PathVariable Long messageId, Long cursor) {
        return commentService.getList(messageId, cursor, PageRequest.of(0, PAGE_DEFAULT_SIZE));
    }


}
