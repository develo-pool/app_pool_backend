package appool.pool.project.message.controller;

import appool.pool.project.message.dto.MessageCreate;
import appool.pool.project.message.dto.MessageEdit;
import appool.pool.project.message.dto.MessageSearch;
import appool.pool.project.message.dto.MessageResponse;
import appool.pool.project.message.service.MessageService;
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

    @PostMapping("/messages")
    public void message(@RequestBody @Valid MessageCreate request) {
        messageService.write(request);
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
