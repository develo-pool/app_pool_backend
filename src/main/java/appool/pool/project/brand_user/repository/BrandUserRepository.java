package appool.pool.project.brand_user.repository;

import appool.pool.project.brand_user.BrandUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BrandUserRepository extends JpaRepository<BrandUser, Long> {

    Optional<BrandUser> findByBrandUsername(String brandUsername);

    boolean existsByBrandUsername(String brandUsername);

    @Query(value = "SELECT * FROM brand_user ORDER BY brand_user_id DESC", nativeQuery = true)
    List<BrandUser> brandList(Pageable pageable);

    @Query(value = "SELECT * FROM brand_user WHERE brand_user_id < :id ORDER BY brand_user_id DESC", nativeQuery = true)
    List<BrandUser> brandListLess(Long id, Pageable pageable);
}
