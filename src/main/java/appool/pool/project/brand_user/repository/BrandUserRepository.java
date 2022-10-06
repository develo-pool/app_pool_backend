package appool.pool.project.brand_user.repository;

import appool.pool.project.brand_user.BrandUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BrandUserRepository extends JpaRepository<BrandUser, Long> {

    Optional<BrandUser> findByBrandUsername(String brandUsername);

    Optional<BrandUser> findByPoolUserId(Long poolUserId);

    boolean existsByBrandUsername(String brandUsername);

    @Query(value = "SELECT * FROM brand_user WHERE pool_user_id IN (SELECT pool_user_id FROM pooluser WHERE user_status = 'BRAND_USER') ORDER BY brand_user_id DESC", nativeQuery = true)
    List<BrandUser> brandList(Pageable pageable);

    @Query(value = "SELECT * FROM brand_user WHERE pool_user_id IN (SELECT pool_user_id FROM pooluser WHERE user_status = 'BRAND_USER') AND brand_user_id < :id ORDER BY brand_user_id DESC", nativeQuery = true)
    List<BrandUser> brandListLess(Long id, Pageable pageable);

    @Query(value = "SELECT * FROM brand_user WHERE pool_user_id IN (SELECT to_user_id FROM follow WHERE from_user_id = :sessionId) ORDER BY brand_user_id DESC", nativeQuery = true)
    List<BrandUser> followingList(long sessionId, Pageable pageable);

    @Query(value = "SELECT * FROM brand_user WHERE pool_user_id IN (SELECT to_user_id FROM follow WHERE from_user_id = :sessionId) AND brand_user_id < :id ORDER BY brand_user_id DESC", nativeQuery = true)
    List<BrandUser> followingListLess(long sessionId, Long id, Pageable pageable);

    @Query(value = "SELECT * FROM brand_user ORDER BY brand_user_id DESC limit 3", nativeQuery = true)
    List<BrandUser> recommendBrand();
}
