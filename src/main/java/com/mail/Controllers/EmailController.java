package com.mail.Controllers;

import com.mail.Dao.MessagesDao;
import com.mail.Entities.TextMessage;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/messages")
public class EmailController {

    MessagesDao mDao;

    @Autowired
    public void setmDao(MessagesDao mDao) {
        this.mDao = mDao;
    }

    @GetMapping()
    public List<TextMessage> getMessages() {
        return mDao.readMessages();
    }

    @GetMapping("/{id}")
    public TextMessage getById(@PathVariable Integer id) {
        return mDao.readMessage(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer create(@RequestBody @Valid TextMessage textMessage) {
        return mDao.addMessage(textMessage);
    }

    @PutMapping("/{id}")
    public TextMessage updateMessage(@PathVariable Integer id, @RequestBody TextMessage textMessage) {
        return mDao.update(id, textMessage);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        mDao.deleteMessage(id);
    }
}
