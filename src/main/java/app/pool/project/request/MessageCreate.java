package app.pool.project.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class MessageCreate {


    private String title;
    private String body;
    private String messageLink;
    private List<MultipartFile> files;

    @Builder
    public MessageCreate(String title, String body, String messageLink, List<MultipartFile> files) {
        this.title = title;
        this.body = body;
        this.messageLink = messageLink;
        this.files = files;
    }
}
