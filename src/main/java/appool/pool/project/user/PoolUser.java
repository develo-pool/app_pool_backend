package appool.pool.project.user;

import appool.pool.project.brand_user.BrandUser;
import appool.pool.project.comment.Comment;
import appool.pool.project.domain.BaseTimeEntity;
import appool.pool.project.message.Message;
import appool.pool.project.welcome.WelcomeMessage;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
@Table(name = "POOLUSER")
@Transactional
public class PoolUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poolUser_id")
    private Long id; // primary key

    @Column(unique = true)
    private String username; // 아이디

    private String password; // 비밀번호

    @Column(unique = true)
    private String nickName; // 회원 닉네임

    private String phoneNumber; // 휴대폰 번호

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus; // 회원 상태(BRAND_USER, USER)

    private String gender; // 성별(MALE, FEMALE)

    @Column(length = 6)
    private String birthday; // 생년월일(6글자)

    private Boolean termAgreement; // 이용약관 동의 여부

    private Boolean privacyAgreement; // 개인정보 처리방침 동의 여부

    @Column(length = 1000)
    private String refreshToken;

    @Column(length = 1000)
    private String fcmToken;

    @Column(name = "category")
    @ElementCollection(targetClass = String.class)
    @Builder.Default
    private List<String> category = new ArrayList<>();


    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Message> messageList = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Comment> commentList = new ArrayList<>();

    @OneToOne(mappedBy = "poolUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private BrandUser brandUser;

    @OneToOne(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private WelcomeMessage welcomeMessage;


    //== 연관 관계 메서드==//
    public void addMessage(Message message) {
        // message의 writer 설정은 message에서 함.
        messageList.add(message);
    }

    public void addComment(Comment comment) {
        // comment의 writer 설정은 comment에서 함.
        commentList.add(comment);
    }

    public void addBrandUser(BrandUser brandUser) {
        this.brandUser = brandUser;
    }

    public void addWelcomeMessage(WelcomeMessage welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }


    //== 정보 수정==//
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void destroyRefreshToken() {
        this.refreshToken = null;
    }

    public void updatePassword(PasswordEncoder passwordEncoder, String password) {
        this.password = passwordEncoder.encode(password);
    }

    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }

    public void updateFCMToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }



    //== 패스워드 암호화 ==//
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    //== 비밀번호 변경, 탈퇴 시, 비밀번호를 확인하며, 이때 비밀번호의 일치여부 확인을 위한 메서드 ==//
    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword) {
        return passwordEncoder.matches(checkPassword, getPassword());
    }

    // 회원가입시, USER 권한 부여하는 메서드
    public void addUserAuthority() {
        this.userStatus = UserStatus.USER;
    }

    public void addBrandUserAuthority() {
        this.userStatus = UserStatus.BRAND_USER;
    }

    public void addWaitingAuthority() {
        this.userStatus = UserStatus.WAITING;
    }


}
