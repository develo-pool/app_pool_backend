package appool.pool.project.welcome.service;

import appool.pool.project.brand_user.repository.BrandUserRepository;
import appool.pool.project.file.service.S3Uploader;
import appool.pool.project.message.exception.MessageException;
import appool.pool.project.message.exception.MessageExceptionType;
import appool.pool.project.user.exception.PoolUserException;
import appool.pool.project.user.exception.PoolUserExceptionType;
import appool.pool.project.user.repository.UserRepository;
import appool.pool.project.util.security.SecurityUtil;
import appool.pool.project.welcome.WelcomeMessage;
import appool.pool.project.welcome.dto.WelcomeMessageCreateDto;
import appool.pool.project.welcome.dto.WelcomeMessageInfoDto;
import appool.pool.project.welcome.repository.WelcomeMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WelcomeMessageService {

    private final WelcomeMessageRepository welcomeMessageRepository;
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;
    private final BrandUserRepository brandUserRepository;

    public void create(WelcomeMessageCreateDto welcomeMessageCreateDto, List<MultipartFile> multipartFiles) {
        WelcomeMessage welcomeMessage = welcomeMessageCreateDto.toEntity();

        welcomeMessage.confirmWriter(userRepository.findByUsername(SecurityUtil.getLoginUsername())
                .orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER)));

        if(multipartFiles != null) {
            welcomeMessage.getFilePath().add(s3Uploader.getThumbnailPath(s3Uploader.uploadImage(multipartFiles).get(0)));
        }

        welcomeMessageRepository.save(welcomeMessage);
    }

    public WelcomeMessageInfoDto get(Long id) {

        WelcomeMessageInfoDto welcomeMessageInfoDto = new WelcomeMessageInfoDto(welcomeMessageRepository.findWithWriterById(id).orElseThrow(() -> new MessageException(MessageExceptionType.MESSAGE_NOT_FOUND)));
        welcomeMessageInfoDto.getWriterDto().getBrandUserInfoDto().setBrandUsername(brandUserRepository.findByPoolUserId(welcomeMessageInfoDto.getWriterDto().getPoolUserId()).get().getBrandUsername());
        welcomeMessageInfoDto.getWriterDto().getBrandUserInfoDto().setBrandProfileImage(brandUserRepository.findByPoolUserId(welcomeMessageInfoDto.getWriterDto().getPoolUserId()).get().getBrandProfileImage());
        return welcomeMessageInfoDto;
    }
}
