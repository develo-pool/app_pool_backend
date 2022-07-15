//package app.pool.project.repository;
//
//import app.pool.project.domain.GenderStatus;
//import app.pool.project.domain.PoolUser;
//import app.pool.project.domain.UserStatus;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class UserRepositoryTest {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    EntityManager em;
//
//    @AfterEach
//    private void after() {
//        em.clear();
//    }
//
//    @Test
//    public void 회원저장_성공() throws Exception {
//        // given
//        PoolUser poolUser = PoolUser.builder()
//                .nickName("닉네임")
//                .password("1234567890")
//                .userStatus(UserStatus.USER)
//                .gender(GenderStatus.MALE)
//                .phoneNumber("01053197659")
//                .build();
//
//        // when
//        PoolUser savePoolUser = userRepository.save(poolUser);
//
//        // then
//        PoolUser findPoolUser = userRepository.findById(savePoolUser.getId())
//                .orElseThrow(() -> new RuntimeException("저장된 회원이 없습니다."));
//        Assertions.assertEquals(findPoolUser, savePoolUser);
//        Assertions.assertEquals(findPoolUser, poolUser);
//    }
//
////    @Test
////    public void 오류_회원가입시_아이디가_없음() throws Exception {
////        // given
////        PoolUser poolUser = PoolUser.builder()
////                .nickName("닉네임")
////                .password("1234567890")
////                .userStatus(UserStatus.USER)
////                .gender(GenderStatus.MALE)
////                .build();
////
////        // expected
////        assertThrows(RuntimeException.class, () -> userRepository.save(poolUser));
////    }
//
//}