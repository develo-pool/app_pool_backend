package appool.pool.project.message.controller;

import appool.pool.project.file.service.AWSS3UploadService;
import appool.pool.project.message.dto.MessageCreate;
import appool.pool.project.message.dto.MessageEdit;
import appool.pool.project.message.dto.MessageSearch;
import appool.pool.project.message.dto.MessageResponse;
import appool.pool.project.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final AWSS3UploadService awss3UploadService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/messages")
    public void message(@RequestPart @Valid MessageCreate request, @RequestPart @Valid List<MultipartFile> multipartFile) {
        List<String> filePaths = awss3UploadService.uploadImage(multipartFile);
        request.filePath().addAll(filePaths);
        messageService.write(request);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity get(@PathVariable Long messageId) {
        return ResponseEntity.ok(messageService.get(messageId));
    }

    @GetMapping("/messages")
    public List<MessageResponse> getList(@ModelAttribute MessageSearch messageSearch) {
        return messageService.getList(messageSearch);
    }

    @PatchMapping("/messages/{messageId}")
    public void edit(@PathVariable Long messageId, @RequestBody @Valid MessageEdit request) {
        messageService.edit(messageId, request);
    }

    @DeleteMapping("/messages/{messageId}")
    public void delete(@PathVariable Long messageId) {
        messageService.delete(messageId);
    }


}
