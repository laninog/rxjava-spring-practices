package org.training.web;

import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.training.domain.Message;
import org.training.repositories.MessageRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/messages")
public class MessageController {

    @Autowired
    private MessageRepository repository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Single<List<Message>> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        return repository.findAll()
                .collect(messages, (l, p) -> l.a)
                .subscribe();
    }

}
