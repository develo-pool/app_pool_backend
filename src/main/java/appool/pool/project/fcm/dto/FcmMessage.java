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
    private Message message;

    @Data
    @AllArgsConstructor
    @Builder
    public static class Message {
        private Notification notification;
        private FcmData data;
        private String token;
    }

    @Builder
    @AllArgsConstructor
    @Data
    public static class Notification {
        private String title;
        private String body;
        private String image;
    }

    @Builder
    @AllArgsConstructor
    @Data
    public static class FcmData {
        private String profileImage;
    }
}
