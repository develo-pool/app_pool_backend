package appool.pool.project.fcm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class FcmMessage {
    private boolean validateOnly;
    private MessageForm messageForm;

    @Data
    @AllArgsConstructor
    @Builder
    public static class MessageForm {
        private Notification notification;
        private List<String> token;
    }

    @Builder
    @AllArgsConstructor
    @Data
    public static class Notification {
        private String title;
        private String body;
        private String image;
    }
}
