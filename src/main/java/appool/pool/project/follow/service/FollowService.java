package appool.pool.project.follow.service;

import appool.pool.project.follow.Follow;
import appool.pool.project.follow.repository.FollowRepository;
import appool.pool.project.user.PoolUser;
import appool.pool.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void follow(long fromUserId, long toUserId) {
        if(followRepository.findFollowByFromUserIdAndToUserId(fromUserId, toUserId) != null) {
            throw new RuntimeException("이미 팔로우 하였습니다.");
        }

        Optional<PoolUser> fromUser = userRepository.findById(fromUserId);
        Optional<PoolUser> toUser = userRepository.findById(toUserId);

        Follow follow = Follow.builder()
                .fromUser(fromUser.get())
                .toUser(toUser.get())
                .build();
        followRepository.save(follow);
    }

    @Transactional
    public void unFollow(long fromUserId, long toUserId) {
        followRepository.unFollow(fromUserId, toUserId);
    }

//    @Transactional
//    public List<FollowDto> getFollower(long profileId, long loginId) {
//        StringBuffer sb = new StringBuffer();
//        sb.append("SELECT u.id, u.name, u.profile_img_url, ");
//        sb.append("if ((SELECT 1 FROM follow WHERE from_user_id = ? AND to_user_id = u.id), TRUE, FALSE) AS followState, ");
//        sb.append("if ((?=u.id), TRUE, FALSE) AS loginUser ");
//        sb.append("FROM user u, follow f ");
//        sb.append("WHERE u.id = f.from_user_id AND f.to_user_id = ?");
//        // 쿼리 완성
//        Query query = em.createNativeQuery(sb.toString())
//                .setParameter(1, loginId)
//                .setParameter(2, loginId)
//                .setParameter(3, profileId);
//
//        //JPA 쿼리 매핑 - DTO에 매핑
//        JpaResultMapper result = new JpaResultMapper();
//        List<FollowDto> followDtoList = result.list(query, FollowDto.class);
//        return followDtoList;
//    }
//
//    @Transactional
//    public List<FollowDto> getFollowing(long profileId, long loginId) {
//        StringBuffer sb = new StringBuffer();
//        sb.append("SELECT u.id, u.name, u.profile_img_url, ");
//        sb.append("if ((SELECT 1 FROM follow WHERE from_user_id = ? AND to_user_id = u.id), TRUE, FALSE) AS followState, ");
//        sb.append("if ((?=u.id), TRUE, FALSE) AS loginUser ");
//        sb.append("FROM user u, follow f ");
//        sb.append("WHERE u.id = f.to_user_id AND f.from_user_id = ?");
//
//        // 쿼리 완성
//        Query query = em.createNativeQuery(sb.toString())
//                .setParameter(1, loginId)
//                .setParameter(2, loginId)
//                .setParameter(3, profileId);
//
//        //JPA 쿼리 매핑 - DTO에 매핑
//        JpaResultMapper result = new JpaResultMapper();
//        List<FollowDto> followDtoList = result.list(query, FollowDto.class);
//        return followDtoList;
//    }

}
