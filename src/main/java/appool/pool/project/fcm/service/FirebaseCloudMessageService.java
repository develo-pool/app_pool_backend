package appool.pool.project.fcm.service;

import appool.pool.project.fcm.dto.FcmMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.boot.json.JsonParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FirebaseCloudMessageService {


    private final String API_URL = "https://fcm.googleapis.com/v1/projects/app-pool-firebase/messages:send";
    private final ObjectMapper objectMapper;

    public void sendMessageTo(String targetToken, String title, String body, String image, String profileImage) throws IOException {

        String message = makeMessage(targetToken, title, body, image, profileImage);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());
    }

    public void sendMessageList(List<String> targetToken, String title, String body, String image, String profileImage) throws IOException {

        targetToken.forEach(f -> {
            String message = null;
            try {
                message = makeMessage(f, title, body, image, profileImage);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(message,
                    MediaType.get("application/json; charset=utf-8"));
            Request request = null;
            try {
                request = new Request.Builder()
                        .url(API_URL)
                        .post(requestBody)
                        .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                        .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                        .build();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                System.out.println(response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });




    }

    private String makeMessage(String targetToken, String title, String body, String image, String profileImage) throws JsonParseException, JsonProcessingException, JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(image)
                                .build()
                        )
                        .data(FcmMessage.FcmData.builder()
                                .profileImage(profileImage)
                                .build())
                        .build()
                ).validateOnly(false).build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "app-pool-firebase-firebase-adminsdk-rn3u9-3a27dcdf01.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/firebase.messaging"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
