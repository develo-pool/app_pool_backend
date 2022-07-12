package app.pool.project.controller;

import app.pool.project.request.MessageCreate;
import app.pool.project.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
}
