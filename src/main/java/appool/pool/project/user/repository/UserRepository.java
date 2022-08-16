package appool.pool.project.user.repository;

import appool.pool.project.user.PoolUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<PoolUser, Long> {

    Optional<PoolUser> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByNickName(String nickName);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<PoolUser> findByRefreshToken(String refreshToken);

    @Query(value = "SELECT fcm_token FROM pooluser WHERE pool_user_id IN (SELECT from_user_id FROM follow WHERE to_user_id = :sessionId) ORDER BY pool_user_id DESC", nativeQuery = true)
    List<String> findFcmTokenList(Long sessionId);
}
 