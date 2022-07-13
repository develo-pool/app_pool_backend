package app.pool.project.controller;

import app.pool.project.request.MessageCreate;
import app.pool.project.request.MessageEdit;
import app.pool.project.request.MessageSearch;
import app.pool.project.response.MessageResponse;
import app.pool.project.service.MessageService;
import app.pool.project.service.PhotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final PhotoService photoService;

    @PostMapping("/messages")
    public void message(@RequestBody @Valid MessageCreate request) throws Exception {
        messageService.write(request, request.getFiles());
    }

    @GetMapping("/messages/{messageId}")
    public MessageResponse get(@PathVariable Long messageId) {
        return messageService.get(messageId);
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
