package appool.pool.project.message.controller;

import appool.pool.project.file.service.S3Uploader;
import appool.pool.project.message.dto.MessageCreate;
import appool.pool.project.message.dto.MessageEdit;
import appool.pool.project.message.dto.MessageSearch;
import appool.pool.project.message.dto.MessageResponse;
import appool.pool.project.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final S3Uploader s3Uploader;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/messages")
    public void message(@ModelAttribute @Valid MessageCreate request, @RequestPart(required = false) List<MultipartFile> multipartFiles) {
        messageService.write(request, multipartFiles);
    }

    @PostMapping("/image")
    public ResponseEntity<List<String>> upload(@RequestPart List<MultipartFile> multipartFile) {
        return ResponseEntity.ok(s3Uploader.uploadImage(multipartFile));
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
