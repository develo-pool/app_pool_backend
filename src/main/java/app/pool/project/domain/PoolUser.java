package app.pool.project.domain;

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
public class PoolUser extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poolUser_id")
    private long id; // primary key

    @Column(unique = true)
    private String username; // 아이디

    private String password; // 비밀번호

    @Column(unique = true)
    private String nickName; // 회원 닉네임 (중복 불가)

    private String phoneNumber; // 휴대폰 번호

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus; // 회원 상태(BRAND_USER, USER, INIT_USER)

    @Enumerated(EnumType.STRING)
    private GenderStatus gender; // 성별(MALE, FEMALE)

    @Column(length = 1000)
    private String refreshToken;



    //== 회원 탈퇴 시 게시글, 댓글 삭제 ==//
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messageList = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();



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


}
