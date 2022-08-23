package appool.pool.project.comment;

import appool.pool.project.domain.BaseTimeEntity;
import appool.pool.project.user.PoolUser;
import appool.pool.project.message.Message;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "COMMENT")
@Builder
@AllArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private PoolUser writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id")
    private Message message;

    @Column(nullable = false)
    private String body;

    //== 연관관계 메서드 ==//
    public void confirmWriter(PoolUser poolUser) {
        this.writer = poolUser;
        writer.addComment(this);
    }

    public void confirmMessage(Message message) {
        this.message = message;
        message.addComment(this);
    }

    //== 수정 ==//
    public void updateBody(String body) {
        this.body = body;
    }

}
