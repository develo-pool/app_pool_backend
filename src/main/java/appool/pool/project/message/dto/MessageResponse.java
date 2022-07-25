package appool.pool.project.message.dto;

import appool.pool.project.message.Message;
import appool.pool.project.user.dto.UserInfoDto;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageResponse {

    private Long postId;
    private String title;
    private String body;
    private String messageLink;
    private String filePath;

    private UserInfoDto writerDto;

    public MessageResponse(Message message) {
        this.postId = message.getId();
        this.title = message.getTitle();
        this.body = message.getBody();
        this.messageLink = message.getMessageLink();
        this.filePath = message.getFilePath();
        this.writerDto = new UserInfoDto(message.getWriter());
    }
}
