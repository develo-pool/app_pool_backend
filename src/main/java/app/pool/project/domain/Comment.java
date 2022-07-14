package app.pool.project.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private PoolUser user;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    @Builder
    public Comment(String body, Message message) {
        this.body = body;
        this.message = message;
    }
}
