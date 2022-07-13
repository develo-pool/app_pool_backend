package app.pool.project.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;


    private String title;
    private String body;
    private String messageLink;

//    @ManyToOne
//    private User user;

    private LocalDateTime createDate;

    private CommentStatus status;

    @OneToMany(mappedBy = "message", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Photo> photo = new ArrayList<>();

    @PrePersist
    public void CreateDate() {
        this.createDate = LocalDateTime.now();
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

    public void addPhoto(Photo photo) {
        this.photo.add(photo);

        if (photo.getMessage() != this) {
            photo.setMessage(this);
        }
    }
}
