package appool.pool.project.follow.controller;

import appool.pool.project.follow.service.FollowService;
import appool.pool.project.user.PoolUser;
import appool.pool.project.user.repository.UserRepository;
import appool.pool.project.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class FollowController {

    private final FollowService followService;
    private final UserRepository userRepository;

    @PostMapping("/follow/{toUserId}")
    public ResponseEntity<?> followUser(@PathVariable long toUserId) {
        Optional<PoolUser> poolUserMy = userRepository.findByUsername(SecurityUtil.getLoginUsername());
        followService.follow(poolUserMy.get().getId(), toUserId);
        return new ResponseEntity<>("팔로우 성공", HttpStatus.OK);
    }

    @DeleteMapping("/follow/{toUserId}")
    public ResponseEntity<?> unFollowUser(@PathVariable long toUserId) {
        Optional<PoolUser> poolUserMy = userRepository.findByUsername(SecurityUtil.getLoginUsername());
        followService.unFollow(poolUserMy.get().getId(), toUserId);
        return new ResponseEntity<>("팔로우 취소 성공", HttpStatus.OK);
    }
}
