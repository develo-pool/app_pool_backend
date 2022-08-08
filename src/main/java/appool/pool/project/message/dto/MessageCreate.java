package appool.pool.project.message.dto;

import appool.pool.project.message.Message;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record MessageCreate(@NotBlank(message = "제목을 입력해주세요") String title,
                            @NotBlank(message = "내용을 입력해주세요") String body,
                            String messageLink) {


    public Message toEntity() {
        return Message.builder()
                .title(title)
                .body(body)
                .messageLink(messageLink)
                .build();
    }
}
