package appool.pool.project.brand_user.repository;

import appool.pool.project.brand_user.BrandUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandUserRepository extends JpaRepository<BrandUser, Long> {

    Optional<BrandUser> findByBrandUsername(String brandUsername);

    boolean existsByBrandUsername(String brandUsername);
}
