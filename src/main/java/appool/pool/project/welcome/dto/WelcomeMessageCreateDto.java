package appool.pool.project.welcome.dto;

import appool.pool.project.welcome.WelcomeMessage;
import javax.validation.constraints.NotBlank;

public record WelcomeMessageCreateDto(@NotBlank(message = "제목을 입력해주세요") String title,
                                      @NotBlank(message = "내용을 입력해주세요") String body,
                                      String messageLink) {
    public WelcomeMessage toEntity() {
        return WelcomeMessage.builder()
                .title(title)
                .body(body)
                .messageLink(messageLink)
                .build();
    }
}
