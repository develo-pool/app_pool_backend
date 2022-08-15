package appool.pool.project.fcm;

import lombok.Data;

@Data
public class RequestDTO {

    private String targetToken;
    private String title;
    private String body;
    private String image;
}
