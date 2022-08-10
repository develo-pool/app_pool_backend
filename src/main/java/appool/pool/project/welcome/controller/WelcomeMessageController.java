package appool.pool.project.welcome.controller;

import appool.pool.project.file.service.S3Uploader;
import appool.pool.project.user.PoolUser;
import appool.pool.project.user.repository.UserRepository;
import appool.pool.project.util.security.SecurityUtil;
import appool.pool.project.welcome.dto.WelcomeMessageCreateDto;
import appool.pool.project.welcome.service.WelcomeMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class WelcomeMessageController {

    private final WelcomeMessageService welcomeMessageService;
    private final UserRepository userRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/welcome")
    public void welcomeMessage(@ModelAttribute @Valid WelcomeMessageCreateDto request, @RequestPart(required = false) List<MultipartFile> multipartFiles) {
        welcomeMessageService.create(request, multipartFiles);
    }

    @GetMapping("/welcome/{writerId}")
    public ResponseEntity get(@PathVariable Long writerId) {
        return ResponseEntity.ok(welcomeMessageService.get(writerId));
    }

    @GetMapping("/welcome")
    public ResponseEntity get() {
        Optional<PoolUser> loginUser = userRepository.findByUsername(SecurityUtil.getLoginUsername());
        return ResponseEntity.ok(welcomeMessageService.get(loginUser.get().getId()));
    }
}
