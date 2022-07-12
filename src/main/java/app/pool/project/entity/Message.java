package app.pool.project.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String body;
    private String messageImg_name;
    private String messageImg_path;
    private String messageLink;

//    @ManyToOne
//    private User user;

    private LocalDateTime createDate;

    private CommentStatus status;

    @PrePersist
    public void CreateDate() {
        this.createDate = LocalDateTime.now();
    }

    @Builder
    public Message(String title, String body, String messageImg_name, String messageImg_path, String messageLink) {
        this.title = title;
        this.body = body;
        this.messageImg_name = messageImg_name;
        this.messageImg_path = messageImg_path;
        this.messageLink = messageLink;
    }
}
