package app.pool.project.user;

import app.pool.project.comment.Comment;
import app.pool.project.domain.BaseTimeEntity;
import app.pool.project.message.Message;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class PoolUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poolUser_id")
    private Long id; // primary key

    @Column(unique = true)
    private String username; // 아이디

    private String password; // 비밀번호

    @Column(unique = true)
    private String nickName; // 회원 닉네임 (중복 불가)

    private String phoneNumber; // 휴대폰 번호

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus; // 회원 상태(BRAND_USER, USER, INIT_USER)

    private String gender; // 성별(MALE, FEMALE)

    @Column(length = 6)
    private Integer birthday; // 생년월일(6글자)

    private Boolean termAgreement; // 이용약관 동의 여부

    private Boolean privacyAgreement; // 개인정보 처리방침 동의 여부

    @Column(length = 1000)
    private String refreshToken;





    //== 회원 탈퇴 시 게시글, 댓글 삭제 ==//
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Message> messageList = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Comment> commentList = new ArrayList<>();



    //== 연관 관계 메서드==//
    public void addMessage(Message message) {
        // message의 writer 설정은 message에서 함.
        messageList.add(message);
    }

    public void addComment(Comment comment) {
        // comment의 writer 설정은 comment에서 함.
        commentList.add(comment);
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


}
