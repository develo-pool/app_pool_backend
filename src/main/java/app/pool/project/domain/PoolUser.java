package app.pool.project.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class PoolUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nickName;
    private String password;
    private String phoneNumber;
//    private List<Category> category;

    @Enumerated(EnumType.STRING)
    private UserStatus status; // 회원 상태(BRAND_USER, USER)

//    @Enumerated(EnumType.STRING)
//    private AuthStatus authenticate; // 문자인증 상태(NOT_YET, DONE)

    @Enumerated(EnumType.STRING)
    private GenderStatus gender; // 성별(MALE, FEMALE)

//    @Enumerated(EnumType.STRING)
//    private NotificationStatus notification; // 알림 수신동의 상태(BRAND_USER, USER)

    private LocalDateTime createDate;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return nickName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
