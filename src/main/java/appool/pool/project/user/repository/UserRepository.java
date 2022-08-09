package appool.pool.project.user.repository;

import appool.pool.project.user.PoolUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<PoolUser, Long> {

    Optional<PoolUser> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByNickName(String nickName);

    Optional<PoolUser> findByRefreshToken(String refreshToken);
}
 