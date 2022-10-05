package appool.pool.project.brand_user.service;

import appool.pool.project.brand_user.BrandUser;
import appool.pool.project.brand_user.dto.BrandUserCreateDto;
import appool.pool.project.brand_user.dto.BrandUserInfoDto;
import appool.pool.project.brand_user.dto.BrandUserUpdate;
import appool.pool.project.brand_user.dto.SlackRequestDto;
import appool.pool.project.brand_user.exception.BrandUserException;
import appool.pool.project.brand_user.exception.BrandUserExceptionType;
import appool.pool.project.brand_user.repository.BrandUserRepository;
import appool.pool.project.file.service.S3Uploader;
import appool.pool.project.follow.repository.FollowRepository;
import appool.pool.project.user.PoolUser;
import appool.pool.project.user.dto.UserInfoDto;
import appool.pool.project.user.exception.PoolUserException;
import appool.pool.project.user.exception.PoolUserExceptionType;
import appool.pool.project.user.repository.UserRepository;
import appool.pool.project.util.security.SecurityUtil;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.block.composition.TextObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;
import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandUserService {

  private final BrandUserRepository brandUserRepository;
  private final UserRepository userRepository;
  private final S3Uploader s3Uploader;
  private final FollowRepository followRepository;

  @Value(value = "${slack.token}")
  private String token;
  @Value(value = "${slack.channel.monitor}")
  private String channel;

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

  public void request(SlackRequestDto slackRequestDto) {

      PoolUser poolUser = userRepository.findByUsername(slackRequestDto.getUsername()).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));
      BrandUser brandUser = brandUserRepository.findByPoolUserId(poolUser.getId()).orElseThrow(() -> new BrandUserException(BrandUserExceptionType.NOT_FOUND_BRAND));

      try{
          List<TextObject> textObjects = new ArrayList<>();
          textObjects.add(markdownText("*사용자 명(Pool_Username):*\n" + poolUser.getUsername()));
          textObjects.add(markdownText("*사용자 아이디(Pool_User_Id):*\n" + poolUser.getId()));
          textObjects.add(markdownText("*브랜드 명(Brand_User_Id):*\n" + brandUser.getBrandUsername()));
          textObjects.add(markdownText("*브랜드 자기소개(Brand_User_Info):*\n" + brandUser.getBrandInfo()));
          textObjects.add(markdownText("*브랜드 신청 시간:*\n" + brandUser.getCreateDate()));

          MethodsClient methods = Slack.getInstance().methods(token);
          ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                  .channel(channel)
                  .blocks(asBlocks(
                          header(header -> header.text(plainText(slackRequestDto.getUsername() + "님이 브랜드 전환을 기다립니다!"))),
                          divider(),
                          section(section -> section.fields(textObjects))
                  )).build();
          methods.chatPostMessage(request);
      } catch (SlackApiException | IOException e) {
          new BrandUserException(BrandUserExceptionType.NOT_FOUND_BRAND);
      }
  }

  public BrandUserInfoDto getBrandInfo(Long id) {
      BrandUser brandUser = brandUserRepository.findById(id).orElseThrow(() -> new BrandUserException(BrandUserExceptionType.NOT_FOUND_BRAND));
      PoolUser poolUser = userRepository.findById(brandUser.getPoolUser().getId()).orElseThrow(() -> new PoolUserException((PoolUserExceptionType.NOT_FOUND_MEMBER)));
      PoolUser loginUser = userRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));

      BrandUserInfoDto brandUserInfoDto = BrandUserInfoDto.builder()
              .brandUsername(brandUser.getBrandUsername())
              .brandInfo(brandUser.getBrandInfo())
              .brandProfileImage(brandUser.getBrandProfileImage())
              .userInfoDto(
                      UserInfoDto.builder()
                              .follow(followRepository.findFollowByFromUserIdAndToUserId(loginUser.getId(), poolUser.getId()) != null)
                              .userFollowerCount(followRepository.findFollowerCountById(poolUser.getId()))
                              .build()
              )
              .build();

      return brandUserInfoDto;
  }

    public BrandUserInfoDto getBrandInfoWeb(Long id) {
        BrandUser brandUser = brandUserRepository.findById(id).orElseThrow(() -> new BrandUserException(BrandUserExceptionType.NOT_FOUND_BRAND));
        PoolUser poolUser = userRepository.findById(brandUser.getPoolUser().getId()).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));

        BrandUserInfoDto brandUserInfoDto = BrandUserInfoDto.builder()
                .brandUsername(brandUser.getBrandUsername())
                .brandInfo(brandUser.getBrandInfo())
                .brandProfileImage(brandUser.getBrandProfileImage())
                .userInfoDto(
                        UserInfoDto.builder()
                                .follow(false)
                                .userFollowerCount(followRepository.findFollowerCountById(poolUser.getId()))
                                .build()
                )
                .build();

        return brandUserInfoDto;
    }

  public BrandUserInfoDto getMyBrandInfo() {
      PoolUser findPoolUser = userRepository.findByUsername((SecurityUtil.getLoginUsername())).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));
      BrandUser brandUser = brandUserRepository.findByPoolUserId(findPoolUser.getId()).orElseThrow(() -> new BrandUserException(BrandUserExceptionType.NOT_FOUND_BRAND));

      BrandUserInfoDto brandUserInfoDto = BrandUserInfoDto.builder()
              .brandUserId(brandUser.getId())
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
      PoolUser loginUser = userRepository.findByUsername((SecurityUtil.getLoginUsername())).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));

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

    public List<BrandUserInfoDto> getBrandsWeb(Long cursor, Pageable pageable) {
        List<BrandUserInfoDto> brandUserList = getBrandList(cursor, pageable).stream()
                .map(BrandUserInfoDto::new)
                .collect(Collectors.toList());
        brandUserList.forEach(f -> {f.setUserInfoDto(UserInfoDto.builder()
                .userFollowerCount(followRepository.findFollowerCountById(f.getPoolUserId()))
                .build());
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

  public void updateBrandInfo(BrandUserUpdate brandUserUpdate, MultipartFile multipartFile) {
      PoolUser loginUser = userRepository.findByUsername((SecurityUtil.getLoginUsername())).orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));
      BrandUser brandUser = brandUserRepository.findByPoolUserId(loginUser.getId()).orElseThrow(() -> new BrandUserException(BrandUserExceptionType.NOT_FOUND_BRAND));
      brandUser.updateBrandInfo(brandUserUpdate.getToBeInfo());
      if(multipartFile != null) {
          brandUser.addProfileImage(s3Uploader.getThumbnailPath(s3Uploader.uploadImageOne(multipartFile)));
      }
  }
}
