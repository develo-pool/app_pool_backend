package appool.pool.project.brand_user.service;

import appool.pool.project.brand_user.BrandUser;
import appool.pool.project.brand_user.dto.BrandUserCreateDto;
import appool.pool.project.brand_user.dto.BrandUserInfoDto;
import appool.pool.project.brand_user.repository.BrandUserRepository;
import appool.pool.project.file.service.S3Uploader;
import appool.pool.project.follow.repository.FollowRepository;
import appool.pool.project.user.PoolUser;
import appool.pool.project.user.dto.UserInfoDto;
import appool.pool.project.user.exception.PoolUserException;
import appool.pool.project.user.exception.PoolUserExceptionType;
import appool.pool.project.user.repository.UserRepository;
import appool.pool.project.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandUserService {

  private final BrandUserRepository brandUserRepository;
  private final UserRepository userRepository;
  private final S3Uploader s3Uploader;
  private final FollowRepository followRepository;

  public void submit(BrandUserCreateDto brandUserCreateDto, MultipartFile multipartFile) throws Exception{
      BrandUser brandUser = brandUserCreateDto.toEntity();
      brandUser.confirmUser(userRepository.findByUsername(SecurityUtil.getLoginUsername())
              .orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER)));

      brandUser.getPoolUser().addWaitingAuthority();

      if(multipartFile != null) {
          brandUser.addProfileImage(s3Uploader.getThumbnailPath(s3Uploader.uploadImageOne(multipartFile)));
      }

      brandUserRepository.save(brandUser);
  }

  public BrandUserInfoDto getBrandInfo(Long id) {
      BrandUser brandUser = brandUserRepository.findById(id).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));
      Optional<PoolUser> poolUser = userRepository.findById(brandUser.getPoolUser().getId());
      Optional<PoolUser> loginUser = userRepository.findByUsername(SecurityUtil.getLoginUsername());

      BrandUserInfoDto brandUserInfoDto = BrandUserInfoDto.builder()
              .brandUsername(brandUser.getBrandUsername())
              .brandInfo(brandUser.getBrandInfo())
              .brandProfileImage(brandUser.getBrandProfileImage())
              .userInfoDto(
                      UserInfoDto.builder()
                              .follow(followRepository.findFollowByFromUserIdAndToUserId(loginUser.get().getId(), poolUser.get().getId()) != null)
                              .userFollowerCount(followRepository.findFollowerCountById(poolUser.get().getId()))
                              .build()
              )
              .build();

      return brandUserInfoDto;
  }

    public BrandUserInfoDto getBrandInfoWeb(Long id) {
        BrandUser brandUser = brandUserRepository.findById(id).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));
        Optional<PoolUser> poolUser = userRepository.findById(brandUser.getPoolUser().getId());

        BrandUserInfoDto brandUserInfoDto = BrandUserInfoDto.builder()
                .brandUsername(brandUser.getBrandUsername())
                .brandInfo(brandUser.getBrandInfo())
                .brandProfileImage(brandUser.getBrandProfileImage())
                .userInfoDto(
                        UserInfoDto.builder()
                                .follow(false)
                                .userFollowerCount(followRepository.findFollowerCountById(poolUser.get().getId()))
                                .build()
                )
                .build();

        return brandUserInfoDto;
    }

  public BrandUserInfoDto getMyBrandInfo() {
      PoolUser findPoolUser = userRepository.findByUsername((SecurityUtil.getLoginUsername()).toString()).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));
      BrandUser brandUser = brandUserRepository.findByPoolUserId(findPoolUser.getId()).get();

      BrandUserInfoDto brandUserInfoDto = BrandUserInfoDto.builder()
              .brandUsername(brandUser.getBrandUsername())
              .brandInfo(brandUser.getBrandInfo())
              .brandProfileImage(brandUser.getBrandProfileImage())
              .userInfoDto(
                      UserInfoDto.builder()
                              .userFollowerCount(followRepository.findFollowerCountById(findPoolUser.getId()))
                              .build()
              )
              .build();
      return brandUserInfoDto;
  }


  public List<BrandUserInfoDto> getBrands(Long cursor, Pageable pageable) {
      PoolUser loginUser = userRepository.findByUsername((SecurityUtil.getLoginUsername()).toString()).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));

      List<BrandUserInfoDto> brandUserList = getBrandList(cursor, pageable).stream()
              .map(BrandUserInfoDto::new)
              .collect(Collectors.toList());
      brandUserList.forEach(f -> {f.setUserInfoDto(UserInfoDto.builder()
                      .userFollowerCount(followRepository.findFollowerCountById(f.getPoolUserId()))
                      .follow(followRepository.findFollowByFromUserIdAndToUserId(loginUser.getId(), f.getPoolUserId())!= null)
              .build());

          if(f.getPoolUserId() == loginUser.getId()) {
              f.setIsLoginUser(true);
          }
      });
      return brandUserList;

  }

  public List<BrandUser> getBrandList(Long id, Pageable page) {
      return id.equals(0L)
              ? brandUserRepository.brandList(page)
              : brandUserRepository.brandListLess(id, page);
  }



  public boolean checkBrandUsernameDuplicate(String brandUsername) {
      return brandUserRepository.existsByBrandUsername(brandUsername);
  }

  public void updateBrandInfo(String toBeInfo) {
      PoolUser loginUser = userRepository.findByUsername((SecurityUtil.getLoginUsername()).toString()).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));
      Optional<BrandUser> brandUser = brandUserRepository.findByPoolUserId(loginUser.getId());
      brandUser.get().updateBrandInfo(toBeInfo);
  }
}
