package appool.pool.project.welcome.dto;

import appool.pool.project.user.dto.UserInfoDto;
import appool.pool.project.welcome.WelcomeMessage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WelcomeMessageInfoDto {

    private Long postId;
    private String title;
    private String body;
    private String messageLink;
    private List<String> filePath;
    private UserInfoDto writerDto;

    public WelcomeMessageInfoDto(WelcomeMessage welcomeMessage) {
        UserInfoDto userInfoDto = UserInfoDto.builder()
                .username(welcomeMessage.getWriter().getUsername())
                .nickName(welcomeMessage.getWriter().getNickName())
                .userStatus(welcomeMessage.getWriter().getUserStatus())
                .build();

        this.postId = welcomeMessage.getId();
        this.title = welcomeMessage.getTitle();
        this.body = welcomeMessage.getBody();
        this.messageLink = welcomeMessage.getMessageLink();
        this.filePath = welcomeMessage.getFilePath();
        this.writerDto = userInfoDto;
    }
}
