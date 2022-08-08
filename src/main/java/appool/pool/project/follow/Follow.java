package appool.pool.project.follow;

import appool.pool.project.user.PoolUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Follow {

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
