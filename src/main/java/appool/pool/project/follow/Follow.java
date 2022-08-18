package appool.pool.project.follow;

import appool.pool.project.domain.BaseTimeEntity;
import appool.pool.project.user.PoolUser;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Transactional
@AllArgsConstructor
public class Follow extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "from_user_id")
    @ManyToOne
    private PoolUser fromUser;

    @JoinColumn(name = "to_user_id")
    @ManyToOne
    private PoolUser toUser;

    @Builder
    public Follow(PoolUser fromUser, PoolUser toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
