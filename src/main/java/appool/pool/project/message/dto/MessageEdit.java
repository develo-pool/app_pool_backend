package appool.pool.project.message.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


public record MessageEdit(
        Optional<String> body,
        Optional<String> messageLink,
        Optional<MultipartFile> uploadFile){

}
