package appool.pool.project.message;

import appool.pool.project.domain.BaseTimeEntity;
import appool.pool.project.comment.Comment;
import appool.pool.project.comment.CommentStatus;
import appool.pool.project.user.PoolUser;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Message extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private PoolUser writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @Column(nullable = true)
    private String messageLink;

    private CommentStatus status;


    //== 메시지 삭제 시, 댓글 전부 삭제 ==//
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();


    //== 연관관계 메서드입니다. ==//
    public void confirmWriter(PoolUser poolUser) {
        this.writer = writer;
        writer.addMessage(this);
    }

    public void addComment(Comment comment) {
        commentList.add(comment);
    }

    @Builder
    public Message(String title, String body, String messageLink) {
        this.title = title;
        this.body = body;
        this.messageLink = messageLink;
    }

    public MessageEditor.MessageEditorBuilder toEditor() {
        return MessageEditor.builder()
                .title(title)
                .body(body)
                .messageLink(messageLink);
    }

    public void edit(MessageEditor messageEditor) {
        title = messageEditor.getTitle();
        body = messageEditor.getBody();
        messageLink = messageEditor.getMessageLink();
    }

}
