package appool.pool.project.brand_user.service;

import appool.pool.project.brand_user.BrandUser;
import appool.pool.project.brand_user.dto.BrandUserCreateDto;
import appool.pool.project.brand_user.dto.BrandUserInfoDto;
import appool.pool.project.brand_user.repository.BrandUserRepository;
import appool.pool.project.user.PoolUser;
import appool.pool.project.user.exception.PoolUserException;
import appool.pool.project.user.exception.PoolUserExceptionType;
import appool.pool.project.user.repository.UserRepository;
import appool.pool.project.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandUserService {

  private final BrandUserRepository brandUserRepository;
  private final UserRepository userRepository;

  public void submit(BrandUserCreateDto brandUserCreateDto) {
      BrandUser brandUser = brandUserCreateDto.toEntity();
      Optional<PoolUser> poolUser = userRepository.findByUsername(brandUser.getPoolUser().getUsername());
      poolUser.get().addBrandUserAuthority();
      brandUserRepository.save(brandUser);
  }

  public BrandUserInfoDto getBrandInfo(Long id) {
      BrandUser brandUser = brandUserRepository.findById(id).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));
      return new BrandUserInfoDto(brandUser);
  }

  public BrandUserInfoDto getMyBrandInfo() {
      PoolUser findPoolUser = userRepository.findByUsername((SecurityUtil.getLoginUsername()).toString()).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));
      BrandUser brandUser = brandUserRepository.findByBrandUsername(findPoolUser.getBrandUser().getBrandUsername()).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));
      return new BrandUserInfoDto(brandUser);
  }

  public boolean checkBrandUsernameDuplicate(String brandUsername) {
      return brandUserRepository.existsByBrandUsername(brandUsername);
  }
}
