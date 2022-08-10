package appool.pool.project.welcome;

import appool.pool.project.domain.BaseTimeEntity;
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
public class WelcomeMessage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "welcomeMessage_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "writer_id")
    private PoolUser writer;

    private String title;

    private String body;

    private String messageLink;

    @Column(nullable = true, name = "filePath")
    @ElementCollection(targetClass = String.class)
    @Builder.Default
    private List<String> filePath = new ArrayList<>();

    public void confirmWriter(PoolUser poolUser) {
        this.writer = writer;
        writer.addWelcomeMessage(this);
    }
}
