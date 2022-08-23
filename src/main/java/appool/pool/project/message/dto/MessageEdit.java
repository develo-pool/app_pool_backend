package appool.pool.project.message.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


public record MessageEdit(
        Optional<String> body,
        Optional<String> messageLink,
        Optional<MultipartFile> uploadFile){

}
