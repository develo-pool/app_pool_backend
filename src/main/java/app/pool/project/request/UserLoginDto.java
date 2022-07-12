package app.pool.project.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class UserLoginDto {
    private String nickName;
    private String password;
    private String phoneNumber;

}
