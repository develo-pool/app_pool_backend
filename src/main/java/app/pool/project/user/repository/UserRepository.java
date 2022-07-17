package app.pool.project.user.repository;

import app.pool.project.user.PoolUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<PoolUser, Long> {

    Optional<PoolUser> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<PoolUser> findByRefreshToken(String refreshToken);
}
