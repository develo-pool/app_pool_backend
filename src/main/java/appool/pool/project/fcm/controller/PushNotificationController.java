package appool.pool.project.fcm.controller;

import appool.pool.project.brand_user.BrandUser;
import appool.pool.project.brand_user.exception.BrandUserException;
import appool.pool.project.brand_user.exception.BrandUserExceptionType;
import appool.pool.project.brand_user.repository.BrandUserRepository;
import appool.pool.project.fcm.dto.RequestDTO;
import appool.pool.project.fcm.dto.RequestSingleDTO;
import appool.pool.project.fcm.service.FirebaseCloudMessageService;
import appool.pool.project.message.Message;
import appool.pool.project.message.exception.MessageException;
import appool.pool.project.message.exception.MessageExceptionType;
import appool.pool.project.message.repository.MessageRepository;
import appool.pool.project.user.PoolUser;
import appool.pool.project.user.exception.PoolUserException;
import appool.pool.project.user.exception.PoolUserExceptionType;
import appool.pool.project.user.repository.UserRepository;
import appool.pool.project.welcome.WelcomeMessage;
import appool.pool.project.welcome.repository.WelcomeMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PushNotificationController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final UserRepository userRepository;
    private final BrandUserRepository brandUserRepository;
    private final MessageRepository messageRepository;
    private final WelcomeMessageRepository welcomeMessageRepository;

    @PostMapping("/api/fcm/welcome")
    public ResponseEntity pushMessage(@RequestBody RequestSingleDTO requestSingleDTO) throws IOException {
        PoolUser poolUser = userRepository.findById(requestSingleDTO.getPool_user_id())
                .orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));
        BrandUser brandUser = brandUserRepository.findByPoolUserId(requestSingleDTO.getBrand_id())
                .orElseThrow(() -> new BrandUserException(BrandUserExceptionType.NOT_FOUND_BRAND));
        WelcomeMessage welcomeMessage = welcomeMessageRepository.findByWriterId(requestSingleDTO.getBrand_id())
                .orElseThrow(() -> new MessageException(MessageExceptionType.MESSAGE_NOT_FOUND));
        if(welcomeMessage.getFilePath().isEmpty()) {
            firebaseCloudMessageService.sendMessageTo(
                    poolUser.getFcmToken(),
                    brandUser.getBrandUsername(),
                    welcomeMessage.getBody(),
                    null,
                    brandUser.getBrandProfileImage());
        }else {
            firebaseCloudMessageService.sendMessageTo(
                    poolUser.getFcmToken(),
                    brandUser.getBrandUsername(),
                    welcomeMessage.getBody(),
                    welcomeMessage.getFilePath().get(0),
                    brandUser.getBrandProfileImage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("api/fcm/submit")
    public ResponseEntity pushMessages(@RequestBody RequestDTO requestDTO) throws IOException {

        BrandUser brandUser = brandUserRepository.findByPoolUserId(requestDTO.getBrand_id())
                .orElseThrow(() -> new BrandUserException(BrandUserExceptionType.NOT_FOUND_BRAND));
        PoolUser poolUser = userRepository.findById(requestDTO.getBrand_id())
                .orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER));
        Message message = messageRepository.findCurrentMessage(poolUser.getId())
                .orElseThrow(() -> new MessageException(MessageExceptionType.MESSAGE_NOT_FOUND));

        List<String> tokenList = userRepository.findFcmTokenList(requestDTO.getBrand_id());


            if(message.getFilePath() == null) {
                firebaseCloudMessageService.sendMessageList(
                        tokenList,
                        brandUser.getBrandUsername(),
                        message.getBody(),
                        null,
                        brandUser.getBrandProfileImage());
            } else {
                firebaseCloudMessageService.sendMessageList(
                        tokenList,
                        brandUser.getBrandUsername(),
                        message.getBody(),
                        message.getFilePath(),
                        brandUser.getBrandProfileImage());
            }

        return ResponseEntity.ok().build();
    }



}
