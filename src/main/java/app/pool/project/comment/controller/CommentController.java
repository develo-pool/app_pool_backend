package app.pool.project.comment.controller;

import app.pool.project.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{messageId}")
    public void comment(@RequestBody @Valid String body, @PathVariable Long messageId) {
        commentService.addComment(body, messageId);
    }

//    @GetMapping("/{messageId}")
//    public CommentResponse getComment(@PathVariable Long messageId) {
//        return commentService.get();
//    }


}
