package appool.pool.project.message.dto;

import appool.pool.project.message.Message;
import javax.validation.constraints.NotBlank;

public record MessageCreate(@NotBlank(message = "내용을 입력해주세요") String body,
                            String messageLink) {


    public Message toEntity() {
        return Message.builder()
                .body(body)
                .messageLink(messageLink)
                .build();
    }
}
