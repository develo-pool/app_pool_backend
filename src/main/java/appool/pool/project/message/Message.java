package appool.pool.project.message;

import appool.pool.project.domain.BaseTimeEntity;
import appool.pool.project.comment.Comment;
import appool.pool.project.comment.CommentStatus;
import appool.pool.project.user.PoolUser;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
@Table(name = "MESSAGE")
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

    @Column(nullable = true, name = "filePath")
    @ElementCollection(targetClass = String.class)
    @Builder.Default
    private List<String> filePath = new ArrayList<>();

    @Column(nullable = true)
    private String messageLink;

    private CommentStatus status;


    //== 메시지 삭제 시, 댓글 전부 삭제 ==//
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comment> commentList = new ArrayList<>();


    //== 연관관계 메서드입니다. ==//
    public void confirmWriter(PoolUser writer) {
        this.writer = writer;
        writer.addMessage(this);
    }

    public void addComment(Comment comment) {
        commentList.add(comment);
    }


    public MessageEditor.MessageEditorBuilder toEditor() {
        return MessageEditor.builder()
                .title(title)
                .body(body)
                .messageLink(messageLink);
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateBody(String body) {
        this.body = body;
    }

    public void updateMessageLink(String messageLink) {
        this.messageLink = messageLink;
    }

}
