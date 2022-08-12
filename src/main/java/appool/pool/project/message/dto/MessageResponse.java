package appool.pool.project.message.dto;

import appool.pool.project.brand_user.dto.BrandUserInfoDto;
import appool.pool.project.message.Message;
import appool.pool.project.user.dto.UserInfoDto;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class MessageResponse {

    private Long postId;
    private String title;
    private String body;
    private String messageLink;
    private List<String> filePath;
    private UserInfoDto writerDto;
    private Boolean commentAble;
    private Boolean isWriter;

    public MessageResponse(Message message) {
        UserInfoDto userInfoDto = UserInfoDto.builder()
                .poolUserId(message.getWriter().getId())
                .username(message.getWriter().getUsername())
                .nickName(message.getWriter().getNickName())
                .userStatus(message.getWriter().getUserStatus())
                .build();

        this.postId = message.getId();
        this.title = message.getTitle();
        this.body = message.getBody();
        this.messageLink = message.getMessageLink();
        this.filePath = message.getFilePath();
        this.writerDto = userInfoDto;
        this.commentAble = false;
        this.isWriter = false;
    }
}
