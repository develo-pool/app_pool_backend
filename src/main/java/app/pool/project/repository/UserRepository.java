package app.pool.project.repository;

import app.pool.project.domain.PoolUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<PoolUser, Long> {

    Optional<PoolUser> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<PoolUser> findByRefreshToken(String refreshToken);
}
