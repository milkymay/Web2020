package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.domain.talk.Talk;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.util.List;


public class TalkService {
    private final TalkRepository talkRepository = new TalkRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    public void save(Talk talk) {
        talkRepository.save(talk);
    }

    public List<Talk> findAll() {
        return talkRepository.findAll();
    }

    public List<Talk> findByTargetOrSourceUserId(User user) {
        return talkRepository.findByTargetOrSourceUserId(user.getId());
    }

    public void validate(String receiver, String text, long id) throws ValidationException {
        if (userRepository.findByLogin(receiver) == null) {
            throw new ValidationException("No such receiver");
        }
        if (Strings.isNullOrEmpty(text)) {
            throw new ValidationException("Message can't be empty");
        }
        if (text.length() > 100) {
            throw new ValidationException("Message is too long");
        }
        if (userRepository.find(id) == null) {
            throw new ValidationException("No such sender");
        }
    }
}
